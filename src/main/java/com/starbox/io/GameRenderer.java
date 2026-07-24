package com.starbox.io;

import com.starbox.GameConstants;
import com.starbox.engine.GameEngine;
import com.starbox.engine.entities.Boss;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Enemy;
import com.starbox.engine.entities.Explosion;
import com.starbox.engine.levels.Level;
import com.starbox.engine.levels.LevelManager;
import com.starbox.engine.levels.LevelType;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Draws stuff
 */
public final class GameRenderer {

    private GameRenderer() {
        // no instances
    }

    /**
     * Main render function to be called once per frame,
     * @param g Graphics2D
     * @param engine GameEngine
     */
    public static void render(Graphics2D g, GameEngine engine) {
        Level level = engine.getLevelManager().current();

        // Background
        g.setColor(level.backgroundColor());
        g.fillRect(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);

        engine.getStarfield().render(g);

        // Entities
        for (Bullet bullet : engine.getBullets()) {
            bullet.render(g);
        }
        for (Enemy enemy : engine.getEnemies()) {
            enemy.render(g);
        }

        for (Explosion explosion: engine.getExplosions()){
            explosion.render(g);
        }

        // Player
        engine.getPlayer().render(g);

        // HUD
        drawHud(g, engine);

        switch (engine.getState()) {
            case LEVEL_INTRO -> drawLevelIntro(g, engine, level);
            case LEVEL_COMPLETE -> drawLevelComplete(g);
            case GAME_OVER -> drawGameOver(g);
            case VICTORY -> drawVictory(g, engine);
            case PLAYING -> {
                // no overlay during normal play
            }
        }
    }

    /**
     * Drawing the HUD
     * @param g A Graphics2D instance
     * @param engine A GameEngine instance
     */
    private static void drawHud(Graphics2D g, GameEngine engine) {
        LevelManager levelManager = engine.getLevelManager();
        Level level = levelManager.current();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        g.drawString("Score: " + engine.getScore(), 10, 20);
        g.drawString("Health: " + engine.getPlayer().getHealth(), 10, 38);
        g.drawString("Lives: " + engine.getPlayer().getLives(), 10, 56);

        String levelLabel = level.levelType() == LevelType.BOSS
                ? "BOSS FIGHT"
                : "Level " + levelManager.levelNumber() + "/" + levelManager.totalLevels();
        int labelWidth = g.getFontMetrics().stringWidth(levelLabel);
        g.drawString(levelLabel, GameConstants.WINDOW_WIDTH - labelWidth - 10, 20);

        // A thin progress bar for the current level -- just two overlapping boxes
        int barX = 10;
        int barY = GameConstants.WINDOW_HEIGHT - 16;
        int barWidth = GameConstants.WINDOW_WIDTH - 20;
        int barHeight = 6;

        double fraction;
        Color fillColor;

        if (level.levelType() == LevelType.BOSS) {
            Boss boss = engine.getBoss();
            fraction = (boss != null) ? (double) boss.getHealth() / boss.getMaxHealth() : 0;
            fillColor = new Color(255, 60, 60, 220);
        } else {
            fraction = levelManager.progress();
            fillColor = new Color(255, 255, 255, 180);
        }

        g.setColor(new Color(255, 255, 255, 60));
        g.fillRect(barX, barY, barWidth, barHeight);

        g.setColor(fillColor);
        int filledWidth = (int) (barWidth * fraction);
        g.fillRect(barX, barY, filledWidth, barHeight);
    }

    private static void drawLevelIntro(Graphics2D g, GameEngine engine, Level level) {
        dimScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));
        drawCentered(g, "LEVEL " + engine.getLevelManager().levelNumber(), GameConstants.WINDOW_HEIGHT / 2 - 20);

        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        drawCentered(g, level.name(), GameConstants.WINDOW_HEIGHT / 2 + 12);
    }

    private static void drawLevelComplete(Graphics2D g) {
        dimScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 28));
        drawCentered(g, "LEVEL COMPLETE", GameConstants.WINDOW_HEIGHT / 2 - 20);

        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        drawCentered(g, "Press E to continue", GameConstants.WINDOW_HEIGHT / 2 + 10);
    }

    private static void drawVictory(Graphics2D g, GameEngine engine) {
        dimScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 28));
        drawCentered(g, "VICTORY!", GameConstants.WINDOW_HEIGHT / 2 - 40);

        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        drawCentered(g, "Final Score: " + engine.getScore(), GameConstants.WINDOW_HEIGHT / 2 - 4);

        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        drawCentered(g, "Press E to play again", GameConstants.WINDOW_HEIGHT / 2 + 26);
    }

    private static void drawGameOver(Graphics2D g) {
        dimScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 28));
        drawCentered(g, "GAME OVER", GameConstants.WINDOW_HEIGHT / 2 - 20);

        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        drawCentered(g, "Press E to restart", GameConstants.WINDOW_HEIGHT / 2 + 10);
    }

    private static void dimScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
    }

    private static void drawCentered(Graphics2D g, String text, int y) {
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (GameConstants.WINDOW_WIDTH - width) / 2, y);
    }
}
