package com.starbox.engine.entities;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {

    protected final EnemyType type;
    protected double age = 0;
    protected int health;
    protected final double baseX;
    protected final double wiggleAmplitude;
    protected final double wiggleFrequency;

    private double shootTimer = 0;
    private double spawnTimer = 0;
    private final List<Bullet> pendingBullets = new ArrayList<>();
    private final List<Enemy> pendingSpawns = new ArrayList<>();

    public Enemy(double x, double y, EnemyType type) {
        super(x, y, type.getSize(), type.getSize(), type.getColor());
        this.type = type;
        this.health = type.getStartingHealth();
        this.velocityY = type.getSpeed();
        this.baseX = x;
        this.wiggleAmplitude = 40 + Math.random() * 40;
        this.wiggleFrequency = 1.0 + Math.random();
    }

    @Override
    public void update(double deltaSeconds) {
        age += deltaSeconds;
        y += velocityY * deltaSeconds;
        x = baseX + Math.sin(age * wiggleFrequency) * wiggleAmplitude;
    }

    public void updateFiring(double dt, Player player) {
        if (!type.canFire() || !isAlive()) return;
        shootTimer -= dt;
        if (shootTimer <= 0) {
            pendingBullets.addAll(type.getFiringBehavior().createBullets(this, player));
            shootTimer = type.getFiringBehavior().getFireIntervalSeconds();
        }
    }

    public void updateSpawning(double dt) {
        if (!type.canSpawn() || !isAlive()) return;
        spawnTimer -= dt;
        if (spawnTimer <= 0) {
            pendingSpawns.addAll(type.getSpawnBehavior().createSpawns(this));
            spawnTimer = type.getSpawnBehavior().getSpawnIntervalSeconds();
        }
    }

    /** Drains and returns any bullets fired since the last call. */
    public List<Bullet> collectFiredBullets() {
        if (pendingBullets.isEmpty()) return List.of();
        List<Bullet> fired = new ArrayList<>(pendingBullets);
        pendingBullets.clear();
        return fired;
    }

    /** Drains and returns any enemies spawned since the last call. */
    public List<Enemy> collectSpawnedEnemies() {
        if (pendingSpawns.isEmpty()) return List.of();
        List<Enemy> spawned = new ArrayList<>(pendingSpawns);
        pendingSpawns.clear();
        return spawned;
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
