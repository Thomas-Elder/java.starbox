package com.starbox.engine.entities;

import java.awt.Color;

/**
 * A simple projectile. Player bullets travel up the screen, enemy bullets
 * travel down. Color differs slightly so you can tell them apart at a glance.
 */
public class Bullet extends Entity {

    public enum Owner { PLAYER, ENEMY }

    private static final int SIZE = 6;
    private final int damage;
    private final Owner owner;

    public Bullet(double x, double y, int damage, Owner owner, double speed) {
        super(x, y, SIZE, SIZE, owner == Owner.PLAYER ? Color.YELLOW : Color.ORANGE);
        this.damage = damage;
        this.owner = owner;
        this.velocityY = speed;
    }

    public Bullet(double x, double y, int size, int damage, Owner owner, double velocityX, double velocityY) {
        super(x, y, size, size, owner == Owner.PLAYER ? Color.YELLOW : Color.ORANGE);
        this.damage = damage;
        this.owner = owner;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public int getDamage() { return damage; }

    public Owner getOwner() {
        return owner;
    }
}
