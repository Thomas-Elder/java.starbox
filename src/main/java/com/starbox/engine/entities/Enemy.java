package com.starbox.engine.entities;

import java.awt.Color;

public class Enemy extends Entity {

    private static final int SIZE = 26;
    private static final double SPEED = 120; // pixels per second, downward

    private double age = 0;
    private final double baseX;
    private final double wiggleAmplitude;
    private final double wiggleFrequency;

    public Enemy(double x, double y) {
        super(x, y, SIZE, SIZE, Color.RED);
        this.velocityY = SPEED;
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
        return 100;
    }
}
