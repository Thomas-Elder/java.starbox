package com.starbox.engine.entities.enemy;

public class Boss extends Enemy {

    private static final double HOVER_Y = 60;
    private static final double VERTICAL_WIGGLE_AMPLITUDE_MIN = 15;
    private static final double VERTICAL_WIGGLE_AMPLITUDE_MAX = 30;

    private final double verticalWiggleAmplitude;
    private final double verticalWiggleFrequency;

    private boolean hovering = false;
    private double hoverAge = 0;

    public Boss(double x, double y, EnemyType type) {
        super(x, y, type);
        this.verticalWiggleAmplitude = VERTICAL_WIGGLE_AMPLITUDE_MIN
                + Math.random() * (VERTICAL_WIGGLE_AMPLITUDE_MAX - VERTICAL_WIGGLE_AMPLITUDE_MIN);
        // a different frequency to horizontal so the two drift out of sync
        // over time, giving a looping/figure-eight feel rather than a
        // straight diagonal line
        this.verticalWiggleFrequency = 0.6 + Math.random() * 1.1;
    }

    @Override
    public void update(double dt) {
        age += dt;

        // x movement
        x = baseX + Math.sin(age * wiggleFrequency) * wiggleAmplitude;

        // y movement
        if (!hovering) {
            y = Math.min(HOVER_Y, y + velocityY * dt);

            if (y >= HOVER_Y) {
                hovering = true;
            }
        } else {
            hoverAge += dt;
            y = HOVER_Y + Math.sin(hoverAge * verticalWiggleFrequency) * verticalWiggleAmplitude;
        }
    }

    @Override
    public boolean diesOnPlayerContact() {
        return false;
    }
}
