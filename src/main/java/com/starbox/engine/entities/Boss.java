package com.starbox.engine.entities;

public class Boss extends Enemy {

    private static final double HOVER_Y = 60;

    public Boss(double x, double y, EnemyType type) {
        super(x, y, type);
    }

    @Override
    public void update(double dt) {
        age += dt;

        // If we're not at the hover height, lower the boss, then wiggle
        if (y < HOVER_Y) {
            y = Math.min(HOVER_Y, y + velocityY * dt);
        } else {
            x = baseX + Math.sin(age * wiggleFrequency) * wiggleAmplitude;
        }
    }

    @Override
    public boolean diesOnPlayerContact() {
        return false;
    }
}
