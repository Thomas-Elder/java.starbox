package com.starbox.engine.entities.player;

import com.starbox.engine.entities.Entity;
import com.starbox.engine.firing.player.PlayerFiringBehavior;

public class Powerup extends Entity {

    private static final int SIZE = 26;
    protected double age = 0;
    private final PowerupType type;
    protected final double baseX;
    protected final double wiggleAmplitude;
    protected final double wiggleFrequency;

    public Powerup(double x, double y, PowerupType type) {
        super(x, y, SIZE, SIZE, type.getColor());

        this.type = type;
        this.velocityY = 80;
        this.baseX = x;
        this.wiggleAmplitude = 40 + Math.random() * 40;
        this.wiggleFrequency = 1.0 + Math.random();
    }

    @Override
    public void update(double dt) {
        // Just copy the Enemy movement for now...
        age += dt;
        y += velocityY * dt;
        x = baseX + Math.sin(age * wiggleFrequency) * wiggleAmplitude;
    }

    public PlayerFiringBehavior getFiringBehavior(){
        return type.getGrantedBehavior();
    }

    /**
     * TODO, getMovementBehavior? A way to create powerups that increase speed eg.
     * @return
     *
    public ?? getMovementBehavior(){}*/
}
