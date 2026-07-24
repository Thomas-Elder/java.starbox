package com.starbox.engine.entities;

import com.starbox.engine.entities.firing.enemy.*;
import com.starbox.engine.entities.spawning.FighterSpawnBehavior;
import com.starbox.engine.entities.spawning.SpawnBehavior;

import java.awt.Color;
import java.util.List;

public enum EnemyType {
    NORMAL(26, 19, 120, 33, 10, Color.RED,
            List.of(),
            null
    ),

    FRIGATE(36, 39, 100, 43, 20, Color.RED,
            List.of(new AimedShotFiring(1.8, 6, 12, 220)),
            null
    ),

    BATTLESHIP(46, 59, 80, 53, 30, Color.RED,
            List.of(new SpreadShotFiring(1.8, 6, 12, 240, 5, 45)),
            null
    ),

    AIRCRAFT_CARRIER(56, 99, 60, 63, 40, Color.RED,
            List.of(new StraightShotFiring(1.8, 6, 12, 280)),
            new FighterSpawnBehavior(6.0, NORMAL)
    ),
    //STATION(46, 59, 0, 53, 30, Color.RED, , ),

    // Boss for the first level
    BOSS(112, 599, 30, 100, 100, Color.RED,
            List.of(new SpreadShotFiring(1.8, 6, 12, 240, 7, 45),
                    new AimedShotFiring(1.2, 6, 12, 220)),
            new FighterSpawnBehavior(6.0, NORMAL)
    );

    private final int size;
    private final int startingHealth;
    private final double speed;
    private final int damage;
    private final int scoreValue;
    private final Color color;
    private final List<FiringBehavior> firingBehaviors;
    private final SpawnBehavior spawnBehavior;

    EnemyType(int size, int startingHealth, double speed, int damage, int scoreValue, Color color,
              List<FiringBehavior> firingBehaviors, SpawnBehavior spawnBehavior) {
        this.size = size;
        this.startingHealth = startingHealth;
        this.speed = speed;
        this.damage = damage;
        this.scoreValue = scoreValue;
        this.color = color;
        this.firingBehaviors = firingBehaviors;
        this.spawnBehavior = spawnBehavior;
    }

    public int getSize() {
        return size;
    }

    public int getStartingHealth() { return startingHealth; }

    public double getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public Color getColor() { return color; }

    public List<FiringBehavior> getFiringBehaviors() { return firingBehaviors; }

    public SpawnBehavior getSpawnBehavior() { return spawnBehavior; }
    public boolean canSpawn() { return spawnBehavior != null; }
}