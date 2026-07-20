package com.starbox.engine.entities;

import java.awt.Color;

/**
 * A simple projectile. Player bullets travel up the screen, enemy bullets
 * travel down. Color differs slightly so you can tell them apart at a glance.
 */
public class Bullet extends Entity {

    public enum Owner { PLAYER, ENEMY }

    private static final int SIZE = 6;

    private final Owner owner;

    public Bullet(double x, double y, Owner owner, double speed) {
        super(x, y, SIZE, SIZE, owner == Owner.PLAYER ? Color.YELLOW : Color.ORANGE);
        this.owner = owner;
        this.velocityY = speed;
    }

    public Owner getOwner() {
        return owner;
    }
}
