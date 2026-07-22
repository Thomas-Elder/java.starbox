package com.starbox.engine.entities;

import java.awt.*;

public class Explosion extends Entity {
    private static final int SIZE = 26;
    private double age = 0;
    private int brightness = 255;

    public Explosion(double x, double y) {
        super(x, y, SIZE, SIZE, new Color(255, 255, 255));
    }

    @Override
    public void update(double deltaSeconds) {
        age += deltaSeconds * 100;
        width += (int) (deltaSeconds * 100);
        height += (int) (deltaSeconds * 100);

        brightness -= (int) (deltaSeconds * 200);
        color = new Color(brightness, brightness, brightness);

        if (age > 120) {
            kill();
        }
    }
}
