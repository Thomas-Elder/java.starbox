package com.starbox.engine.entities;

public class Enemy extends Entity {

    protected final EnemyType type;
    protected double age = 0;
    protected int health;
    protected final double baseX;
    protected final double wiggleAmplitude;
    protected final double wiggleFrequency;

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



    public void damage(int damage) {
        health -= damage;

        if (health <= 0) {
            health = 0;
            kill();
        }
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
