package com.starbox.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Starfield {

    private static final class Star {
        double x;
        double y;
        final double speed;
        final int size;
        final Color color;

        Star(double x, double y, double speed, int size, Color color) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = size;
            this.color = color;
        }
    }

    private final List<Star> stars = new ArrayList<>();
    private final int width;
    private final int height;
    private final Random random = new Random();

    /**
     * @param farColor  color for the slow, dim, distant layer
     * @param midColor  color for the medium-speed, medium-distance layer
     * @param nearColor color for the fast, bright, close layer
     */
    public Starfield(int width, int height, Color farColor, Color midColor, Color nearColor) {
        this.width = width;
        this.height = height;

        generateLayer(60, 20, 40, 1, farColor);
        generateLayer(40, 40, 70, 2, midColor);
        generateLayer(25, 70, 110, 2, nearColor);
    }

    private void generateLayer(int count, double minSpeed, double maxSpeed, int size, Color color) {
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * width;
            double y = random.nextDouble() * height;
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
            stars.add(new Star(x, y, speed, size, color));
        }
    }

    public void update(double deltaSeconds) {
        for (Star star : stars) {
            star.y += star.speed * deltaSeconds;
            if (star.y > height) {
                star.y = -star.size;
                star.x = random.nextDouble() * width;
            }
        }
    }

    public void render(Graphics2D g) {
        for (Star star : stars) {
            g.setColor(star.color);
            g.fillRect((int) star.x, (int) star.y, star.size, star.size);
        }
    }
}
