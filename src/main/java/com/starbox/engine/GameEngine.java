package com.starbox.engine;

import com.starbox.GameConstants;
import com.starbox.engine.entities.*;
import com.starbox.engine.levels.Level;
import com.starbox.engine.levels.LevelManager;
import com.starbox.engine.levels.LevelType;
import com.starbox.engine.levels.Levels;
import com.starbox.io.InputHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Owns and updates all simulation state: the player, bullets, enemies,
 * starfield, current level, score, and the overall {@link GameState}
 * machine (level intro -> playing -> level complete/game over/victory).
 */
public class GameEngine {

    private final LevelManager levelManager = new LevelManager(Levels.campaign());
    private final Player player;
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Explosion> explosions = new ArrayList<>();
    private final Random random = new Random();

    private Starfield starfield;
    private GameState state = GameState.LEVEL_INTRO;
    private double stateTimer = 0;
    private int spawnIndex = 0;
    private int score = 0;
    private Boss boss;

    public GameEngine() {
        Level firstLevel = levelManager.current();
        starfield = buildStarfield(firstLevel);

        player = new Player(
                GameConstants.WINDOW_WIDTH / 2.0 - 14,
                GameConstants.WINDOW_HEIGHT - 100);
    }

    private static Starfield buildStarfield(Level level) {
        return new Starfield(
                GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT,
                level.farStarColor(), level.midStarColor(), level.nearStarColor());
    }

    // ------------------------------------------------------------------
    // Update
    // ------------------------------------------------------------------

    public void update(double dt, InputHandler input) {
        starfield.update(dt);

        switch (state) {
            case LEVEL_INTRO -> updateLevelIntro(dt, input);
            case PLAYING -> updatePlaying(dt, input);
            case LEVEL_COMPLETE -> updateLevelComplete(dt, input);
            case GAME_OVER, VICTORY -> updateEndScreen(dt, input);
        }
    }

    private void updateLevelIntro(double dt, InputHandler input) {
        stateTimer += dt;

        player.move(dt, input.isUpPressed(), input.isDownPressed(),
                input.isLeftPressed(), input.isRightPressed(),
                GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);

        if (stateTimer >= GameConstants.LEVEL_INTRO_SECONDS || input.isEJustPressed()) {
            state = GameState.PLAYING;
            stateTimer = 0;
        }
    }

    private void updatePlaying(double dt, InputHandler input) {
        Level level = levelManager.current();

        player.move(dt, input.isUpPressed(), input.isDownPressed(),
                input.isLeftPressed(), input.isRightPressed(),
                GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);

        if (input.isShootPressed() && player.canShoot()) {
            bullets.addAll(player.fire());
        }

        if (level.levelType() == LevelType.BOSS) {
            spawnBossIfNeeded(level);
        } else {
            spawnScheduledEnemies();
        }

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }

        List<Enemy> spawnedByEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update(dt);
            enemy.updateFiring(dt, player);
            enemy.updateSpawning(dt);
            bullets.addAll(enemy.collectFiredBullets());
            spawnedByEnemies.addAll(enemy.collectSpawnedEnemies());
        }
        enemies.addAll(spawnedByEnemies);

        var collisionResult = CollisionSystem.resolve(player, bullets, enemies);

        score += collisionResult.scoreGained();
        explosions.addAll(collisionResult.explosions());

        for (Explosion explosion : explosions){
            explosion.update(dt);
        }

        removeOffscreenAndDead();

        if (!player.isAlive()) {
            state = GameState.GAME_OVER;
            stateTimer = 0;
            return;
        }

        // If it's a boss level, and a not null boss is dead, complete the level
        boolean levelComplete;
        if (level.levelType() == LevelType.BOSS) {
            levelComplete = boss != null && !boss.isAlive();
        } else {
            levelManager.tick(dt);
            levelComplete = levelManager.isComplete();
        }

        if (levelComplete) {
            bullets.clear();
            enemies.clear();
            boss = null;
            state = levelManager.isFinalLevel() ? GameState.VICTORY : GameState.LEVEL_COMPLETE;
            stateTimer = 0;
        }
    }

    private void updateLevelComplete(double dt, InputHandler input) {
        stateTimer += dt;

        player.move(dt, input.isUpPressed(), input.isDownPressed(),
                input.isLeftPressed(), input.isRightPressed(),
                GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);

        for (Explosion explosion : explosions){
            explosion.update(dt);
        }

        if (input.isEJustPressed()) {
            goToNextLevel();
        }
    }

    private void updateEndScreen(double dt, InputHandler input) {
        stateTimer += dt;
        if (input.isEJustPressed()) {
            restartCampaign();
        }
    }

    private void goToNextLevel() {
        if (levelManager.advance()) {
            Level level = levelManager.current();
            starfield = buildStarfield(level);
            state = GameState.LEVEL_INTRO;
            stateTimer = 0;
            spawnIndex = 0;
            boss = null;
            return;
        }

        // Do something with this information
        state = GameState.VICTORY;
    }

    private void restartCampaign() {
        bullets.clear();
        enemies.clear();
        score = 0;
        levelManager.reset();

        Level level = levelManager.current();
        starfield = buildStarfield(level);

        player.reset(GameConstants.WINDOW_WIDTH / 2.0 - 14, GameConstants.WINDOW_HEIGHT - 100);

        state = GameState.LEVEL_INTRO;
        stateTimer = 0;
        spawnIndex = 0;
    }

    private void spawnScheduledEnemies(){
        List<SpawnEntry> schedule = levelManager.current().spawnSchedule();
        double elapsedSeconds = levelManager.getElapsedSeconds();

        // While there are more enemies to spawn in the schedule, and the next Enemy to spawn is prior to
        // the elapsed time.
        // So if the next spawn is due at 5 seconds, but elapsed time is 4.5, we skip the loop
        // Once elapsed time increase to 5.1, we go into the loop.
        // It's a loop incase multiple spawns are scheduled at the same time and need to be
        // instantiated in the same tick.
        while (spawnIndex < schedule.size() && schedule.get(spawnIndex).atSeconds() <= elapsedSeconds) {
            EnemyType type = schedule.get(spawnIndex).type();
            double x = random.nextDouble() * (GameConstants.WINDOW_WIDTH - type.getSize());
            enemies.add(new Enemy(x, -type.getSize(), type));
            spawnIndex++;
        }
    }

    private void spawnBossIfNeeded(Level level) {
        if (boss != null) { return; }

        EnemyType type = level.bossType();
        double x = (GameConstants.WINDOW_WIDTH - type.getSize()) / 2.0;
        boss = new Boss(x, -type.getSize(), type);
        enemies.add(boss);
    }

    private void removeOffscreenAndDead() {
        bullets.removeIf(b -> !b.isAlive() || b.getY() < -20 || b.getY() > GameConstants.WINDOW_HEIGHT + 20);
        enemies.removeIf(e -> !e.isAlive() || e.getY() > GameConstants.WINDOW_HEIGHT + 40);
        explosions.removeIf(e -> !e.isAlive());
    }

    // ------------------------------------------------------------------
    // Read-only accessors for rendering
    // ------------------------------------------------------------------

    public GameState getState() {
        return state;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getBullets() {
        return Collections.unmodifiableList(bullets);
    }

    public List<Enemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    public Boss getBoss() {
        return boss;
    }

    public List<Explosion> getExplosions() {
        return Collections.unmodifiableList(explosions);
    }

    public Starfield getStarfield() {
        return starfield;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public int getScore() {
        return score;
    }
}
