package com.starbox.engine;

import com.starbox.GameConstants;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Enemy;
import com.starbox.engine.entities.Explosion;
import com.starbox.engine.entities.Player;
import com.starbox.engine.levels.Level;
import com.starbox.engine.levels.LevelManager;
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

    private double enemySpawnTimer = 0;
    private double enemySpawnInterval;
    private int score = 0;

    public GameEngine() {
        Level firstLevel = levelManager.current();
        starfield = buildStarfield(firstLevel);
        enemySpawnInterval = firstLevel.baseEnemySpawnInterval();

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
        if (stateTimer >= GameConstants.LEVEL_INTRO_SECONDS || input.isShootJustPressed()) {
            state = GameState.PLAYING;
            stateTimer = 0;
        }
    }

    private void updatePlaying(double dt, InputHandler input) {
        player.move(dt, input.isUpPressed(), input.isDownPressed(),
                input.isLeftPressed(), input.isRightPressed(),
                GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);

        if (input.isShootPressed() && player.canShoot()) {
            bullets.add(new Bullet(
                    player.getX() + player.getWidth() / 2.0 - 3,
                    player.getY(),
                    Bullet.Owner.PLAYER,
                    -480));
            player.resetShootTimer();
        }

        spawnEnemies(dt);

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }

        var collisionResult = CollisionSystem.resolve(player, bullets, enemies);
        if (collisionResult.collision) {
            score += collisionResult.points;
            for (Coord coords : collisionResult.coords) {
                explosions.add(new Explosion(coords.x, coords.y));
            }
        }

        for (Explosion explosion : explosions){
            explosion.update(dt);
        }

        removeOffscreenAndDead();

        if (!player.isAlive()) {
            state = GameState.GAME_OVER;
            stateTimer = 0;
            return;
        }

        levelManager.tick(dt);
        if (levelManager.isComplete()) {
            bullets.clear();
            enemies.clear();
            state = levelManager.isFinalLevel() ? GameState.VICTORY : GameState.LEVEL_COMPLETE;
            stateTimer = 0;
        }
    }

    private void updateLevelComplete(double dt, InputHandler input) {
        stateTimer += dt;
        if (input.isShootJustPressed()) {
            goToNextLevel();
        }
    }

    private void updateEndScreen(double dt, InputHandler input) {
        stateTimer += dt;
        if (input.isShootJustPressed()) {
            restartCampaign();
        }
    }

    private void goToNextLevel() {
        if (levelManager.advance()) {
            Level level = levelManager.current();
            starfield = buildStarfield(level);
            enemySpawnInterval = level.baseEnemySpawnInterval();
            enemySpawnTimer = 0;
            state = GameState.LEVEL_INTRO;
            stateTimer = 0;
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
        enemySpawnInterval = level.baseEnemySpawnInterval();
        enemySpawnTimer = 0;

        player.reset(GameConstants.WINDOW_WIDTH / 2.0 - 14, GameConstants.WINDOW_HEIGHT - 100);

        state = GameState.LEVEL_INTRO;
        stateTimer = 0;
    }

    private void spawnEnemies(double dt) {
        enemySpawnTimer -= dt;
        if (enemySpawnTimer <= 0) {
            enemySpawnTimer = enemySpawnInterval;
            double x = random.nextDouble() * (GameConstants.WINDOW_WIDTH - 26);
            enemies.add(new Enemy(x, -30));
            enemySpawnInterval = Math.max(0.4, enemySpawnInterval - 0.01);
        }
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
