package com.starbox.engine.entities.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Entity;
import com.starbox.engine.firing.FiredShot;
import com.starbox.engine.firing.player.PlayerFiringBehavior;
import com.starbox.engine.firing.player.SingleShotFiring;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The player-controlled ship. Just a green square that can move in any
 * direction and fire bullets with a small cooldown between shots.
 */
public class Player extends Entity {

    private static final class FiringSlot {
        final PlayerFiringBehavior behavior;
        double cooldownRemaining = 0; // ready to shoot as soon as a behavior is added.
        double durationRemaining;

        FiringSlot(PlayerFiringBehavior behavior) {
            this.behavior = behavior;
            this.durationRemaining = behavior.getDurationSeconds();
        }
    }

    private static final int SIZE = 28;
    private static final double SPEED = 300; // pixels per second

    private final List<Player.FiringSlot> firingSlots = new ArrayList<>();

    private int health = 100;
    private int lives = 3;

    public Player(double x, double y) {
        super(x, y, SIZE, SIZE, Color.GREEN);

        // Add default single shot firing behavior
        firingSlots.add(new Player.FiringSlot(new SingleShotFiring(0.18, Double.POSITIVE_INFINITY, 6, 10, 480 )));
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

        x = Math.clamp(x, 0, worldWidth - width);
        y = Math.clamp(y, 0, worldHeight - height);
    }

    /**
     * For each firing behavior the player currently has, tick the cooldown and duration.
     * @param dt deltatime
     */
    public void tickFiringCooldowns(double dt) {
        for (FiringSlot slot : firingSlots) {
            slot.cooldownRemaining -= dt;
            slot.durationRemaining -= dt;
        }
        firingSlots.removeIf(slot -> slot.durationRemaining <= 0);
    }

    /**
     * Fires all bullets created by all firing behaviors not on cooldown.
     * @return a List of FiredShots this tick.
     */
    public List<FiredShot> fire() {
        if (!alive) return List.of();

        List<FiredShot> shots = new ArrayList<>();
        for (FiringSlot slot : firingSlots) {
            if (slot.cooldownRemaining <= 0) {
                List<Bullet> bullets = slot.behavior.createBullets(this);
                shots.add(new FiredShot(slot.behavior.getShotEventType(), bullets));
                slot.cooldownRemaining = slot.behavior.getCooldownSeconds();
            }
        }
        return shots;
    }

    public void addFiringBehavior(PlayerFiringBehavior firingBehavior){ firingSlots.add(new FiringSlot(firingBehavior)); }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Applies damage. When health hits zero a life is lost and health
     * resets, unless it was the last life, in which case the player dies.
     * @param amount the amount of damage taken
     * @return true if this damage was lethal
     */
    public boolean damage(int amount) {
        health -= amount;
        if (health <= 0) {
            lives--;
            if (lives > 0) {
                health = 100;
            } else {
                health = 0;
                kill();
                return true;
            }
        }

        return false;
    }

    public void reset(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.lives = 3;
        this.alive = true;

        // Reset firing slots
        firingSlots.clear();
        firingSlots.add(new Player.FiringSlot(new SingleShotFiring(0.18, Double.POSITIVE_INFINITY, 6, 10, 480 )));
    }
}
