package com.starbox.engine.entities;

import java.awt.Color;

public enum EnemyType {
    NORMAL(26, 120, 33, 10, Color.RED),
    FRIGATE(36, 100, 43, 20, Color.RED),
    BATTLESHIP(46, 80, 53, 30, Color.RED),
    AIRCRAFT_CARRIER(56, 60, 63, 40, Color.RED),
    STATION(46, 0, 53, 30, Color.RED),
    BOSS(112, 20, 100, 100, Color.RED);

    private final int size;
    private final double speed;
    private final int damage;
    private final int scoreValue;
    private final Color color;

    EnemyType(int size, double speed, int damage, int scoreValue, Color color) {
        this.size = size;
        this.speed = speed;
        this.damage = damage;
        this.scoreValue = scoreValue;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public Color getColor() {
        return color;
    }
}