package com.starbox.engine.entities;

import java.awt.*;

public class Explosion extends Entity {

    private static final double DURATION_SECONDS = 1.0;
    private static final int START_SIZE = 10;
    private static final int END_SIZE = 50;

    private final double centerX;
    private final double centerY;
    private double progress = 0;

    /**
     * Instantiates an Explosion with x, y coordinates as the top left corner.
     * @param x x-coordinate of the top left corner of the explosion
     * @param y y-coordinate of the top left corner of the explosion
     */
    public Explosion(double x, double y) {
        super(x, y, START_SIZE, START_SIZE, Color.WHITE);
        this.centerX = x + START_SIZE / 2.0;
        this.centerY = y + START_SIZE / 2.0;
    }

    /**
     * Returns an Explosion centered on the x, y coords given.
     * @param centerX x-coordinate of the center of the explosion
     * @param centerY y-coordinate of the center of the explosion
     * @return an Explosion instance
     */
    public static Explosion centeredAt(double centerX, double centerY) {
        return new Explosion(centerX - START_SIZE / 2.0, centerY - START_SIZE / 2.0);
    }

    /**
     *
     * @param deltaSeconds number of seconds in a tick, used to keep updates in sync.
     */
    @Override
    public void update(double deltaSeconds) {

        progress = Math.min(1.0, progress + deltaSeconds / DURATION_SECONDS);

        width = (int) lerp(START_SIZE, END_SIZE, progress);
        height = width;

        x = centerX - width / 2.0;
        y = centerY - height / 2.0;

        if (progress >= 1.0) {
            kill();
        }
    }

    /**
     * Overriding the render function here so we can reset the color.
     * @param g Graphics2D
     */
    @Override
    public void render(Graphics2D g){
        int alpha = (int) lerp(255, 0, progress);
        g.setColor(new Color(255, 255, 255, alpha));
        g.fillRect((int) Math.round(x), (int) Math.round(y), width, height);
    }

    /**
     * LERP - linear interpolation!
     * A function for finding a value between two values based on a given ratio.
     * @param from start value
     * @param to end value
     * @param ratio a number from 0-1 representing how far through the range we are
     * @return
     */
    private static double lerp(double from, double to, double ratio){
        return from + (to - from) * ratio;
    }
}
