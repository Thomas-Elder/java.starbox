package com.starbox.engine.entities.enemy;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Entity;
import com.starbox.engine.entities.player.Player;
import com.starbox.engine.firing.FiredShot;
import com.starbox.engine.firing.enemy.FiringBehavior;

import java.util.ArrayList;
import java.util.List;



public class Enemy extends Entity {

    /**
     * Private helper class. Notes for future Tom:
     * static here does not mean "cannot be instantiated", it means "decoupled from the
     * surrounding context".
     * In the more common context you're used to, with functions being static, it means
     * the function is not dependent on an instance of the class. And therefore you cannot
     * call new Class().staticFunction(), the function has no reference to an instance.
     * Here it means this class is not tied to an instance of Enemy. That does not mean
     * that we cannot instantiate FiringSlot though!
     * In fact, if we made FiringSlot non-static, we'd have problems. Every time we create a
     * FiringSlot, a reference to the instance of Enemy creating it is passed into
     * FiringSlot, and we end up with many refs to a single instance. This can cause
     * performance issues.
     */
    private static final class FiringSlot {
        final FiringBehavior behavior;
        double cooldown;

        FiringSlot(FiringBehavior behavior) {
            this.behavior = behavior;
            this.cooldown = behavior.getFireIntervalSeconds();
        }
    }

    protected final EnemyType type;
    protected double age = 0;
    protected int health;
    protected final double baseX;
    protected final double wiggleAmplitude;
    protected final double wiggleFrequency;

    // private double shootTimer = 0;
    private double spawnTimer = 0;
    private final List<FiringSlot> firingSlots = new ArrayList<>();

    public Enemy(double x, double y, EnemyType type) {
        super(x, y, type.getSize(), type.getSize(), type.getColor());
        this.type = type;
        this.health = type.getStartingHealth();
        this.velocityY = type.getSpeed();
        this.baseX = x;
        this.wiggleAmplitude = 40 + Math.random() * 40;
        this.wiggleFrequency = 1.0 + Math.random();

        for (FiringBehavior behavior : type.getFiringBehaviors()) {
            firingSlots.add(new FiringSlot(behavior));
        }
        if (type.canSpawn()) {
            this.spawnTimer = type.getSpawnBehavior().getSpawnIntervalSeconds();
        }
    }

    @Override
    public void update(double deltaSeconds) {
        age += deltaSeconds;
        y += velocityY * deltaSeconds;
        x = baseX + Math.sin(age * wiggleFrequency) * wiggleAmplitude;
    }

    public List<FiredShot> fire(double dt, Player player) {

        if (!alive) return List.of();

        List<FiredShot> shots = new ArrayList<>();
        for (FiringSlot slot : firingSlots) {
            slot.cooldown -= dt;
            if (slot.cooldown <= 0) {
                List<Bullet> bullets = slot.behavior.createBullets(this, player);
                shots.add(new FiredShot(slot.behavior.getShotEventType(), bullets));
                slot.cooldown = slot.behavior.getFireIntervalSeconds();
            }
        }
        return shots;
    }

    public List<Enemy> spawn(double dt) {
        if (!type.canSpawn() || !isAlive()) return List.of();

        spawnTimer -= dt;
        if (spawnTimer <= 0) {
            spawnTimer = type.getSpawnBehavior().getSpawnIntervalSeconds();
            return type.getSpawnBehavior().createSpawns(this);
        }
        return List.of();
    }

    public boolean damage(int damage) {
        health -= damage;

        if (health <= 0) {
            health = 0;
            kill();
            return true;
        }

        return false;
    }

    public boolean diesOnPlayerContact() {
        return true;
    }

    public int getMaxHealth() { return type.getStartingHealth(); }
    public int getHealth() { return health; }
    public int getScoreValue() {
        return type.getScoreValue();
    }
    public int getDamage(){ return type.getDamage(); }
}
