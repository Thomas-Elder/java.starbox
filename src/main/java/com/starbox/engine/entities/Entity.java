package com.starbox.engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Base class for every object in the game.
 */
public abstract class Entity {

    protected double x;
    protected double y;
    protected double velocityX;
    protected double velocityY;
    protected final int width;
    protected final int height;
    protected Color color;
    protected boolean alive = true;

    protected Entity(double x, double y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(double deltaSeconds) {
        x += velocityX * deltaSeconds;
        y += velocityY * deltaSeconds;
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int) Math.round(x), (int) Math.round(y), width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) Math.round(x), (int) Math.round(y), width, height);
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
