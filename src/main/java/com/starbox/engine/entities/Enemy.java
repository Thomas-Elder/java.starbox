package com.starbox.engine.entities;

import java.awt.Color;

public class Enemy extends Entity {

    private final EnemyType type;
    private double age = 0;
    private final double baseX;
    private final double wiggleAmplitude;
    private final double wiggleFrequency;

    public Enemy(double x, double y, EnemyType type) {
        super(x, y, type.getSize(), type.getSize(), Color.RED);
        this.type = type;
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

    public int getScoreValue() {
        return type.getScoreValue();
    }

    public int getDamage(){
        return type.getDamage();
    }
}
