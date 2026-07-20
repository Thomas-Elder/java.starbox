package com.starbox.engine.entities;

import java.awt.Color;

/**
 * The player-controlled ship. Just a green square that can move in any
 * direction and fire bullets with a small cooldown between shots.
 */
public class Player extends Entity {

    private static final int SIZE = 28;
    private static final double SPEED = 300; // pixels per second
    private static final double SHOOT_COOLDOWN = 0.18; // seconds between shots

    private double shootTimer = 0;
    private int health = 100;
    private int lives = 3;

    public Player(double x, double y) {
        super(x, y, SIZE, SIZE, Color.GREEN);
    }

    /**
     * Moves the player based on which direction keys are held, clamping the
     * result so the ship can never leave the play field.
     */
    public void move(double deltaSeconds, boolean up, boolean down, boolean left, boolean right,
                      int worldWidth, int worldHeight) {
        velocityX = 0;
        velocityY = 0;
        if (up) velocityY -= SPEED;
        if (down) velocityY += SPEED;
        if (left) velocityX -= SPEED;
        if (right) velocityX += SPEED;

        super.update(deltaSeconds);

        x = clamp(x, 0, worldWidth - width);
        y = clamp(y, 0, worldHeight - height);

        if (shootTimer > 0) {
            shootTimer -= deltaSeconds;
        }
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public boolean canShoot() {
        return alive && shootTimer <= 0;
    }

    public void resetShootTimer() {
        shootTimer = SHOOT_COOLDOWN;
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Applies damage. When health hits zero a life is lost and health
     * resets, unless it was the last life, in which case the player dies.
     */
    public void damage(int amount) {
        health -= amount;
        if (health <= 0) {
            lives--;
            if (lives > 0) {
                health = 100;
            } else {
                health = 0;
                kill();
            }
        }
    }

    public void reset(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.lives = 3;
        this.shootTimer = 0;
        this.alive = true;
    }
}
