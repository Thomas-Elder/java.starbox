package com.starbox.engine.entities.enemy;

import com.starbox.engine.firing.enemy.AimedShotFiring;
import com.starbox.engine.firing.enemy.FiringBehavior;
import com.starbox.engine.firing.enemy.SpreadShotFiring;
import com.starbox.engine.firing.enemy.StraightShotFiring;
import com.starbox.engine.spawn.FighterSpawnBehavior;
import com.starbox.engine.spawn.SpawnBehavior;

import java.awt.Color;
import java.util.List;

public enum EnemyType {
    NORMAL(26, 19, 120, 33, 10, Color.RED,
            List.of(),
            null
    ),

    FRIGATE(36, 39, 100, 43, 20, Color.RED,
            List.of(new AimedShotFiring(1.8, 16, 30, 220)),
            null
    ),

    BATTLESHIP(46, 59, 80, 53, 30, Color.RED,
            List.of(new SpreadShotFiring(1.8, 6, 12, 240, 7, 45)),
            null
    ),

    AIRCRAFT_CARRIER(56, 99, 60, 63, 40, Color.RED,
            List.of(new StraightShotFiring(1.8, 10, 20, 280)),
            new FighterSpawnBehavior(6.0, NORMAL)
    ),
    //STATION(46, 59, 0, 53, 30, Color.RED, , ),

    // Boss for the first level
    BOSS_ONE(112, 599, 30, 100, 100, Color.RED,
            List.of(new SpreadShotFiring(1.8, 6, 12, 240, 7, 45),
                    new AimedShotFiring(1.2, 16, 30, 220)),
            new FighterSpawnBehavior(6.0, NORMAL)
    ),

    // Boss for the second level
    BOSS_TWO(112, 599, 30, 100, 100, Color.RED,
            List.of(new SpreadShotFiring(1.5, 6, 12, 250, 8, 50),
                    new AimedShotFiring(1.0, 16, 30, 230)),
            new FighterSpawnBehavior(5.0, NORMAL)
    ),

    // Boss for the second level
    BOSS_THREE(112, 599, 30, 100, 100, Color.RED,
            List.of(new SpreadShotFiring(1.3, 6, 12, 260, 9, 55),
                    new AimedShotFiring(0.8, 16, 30, 240)),
            new FighterSpawnBehavior(4.0, NORMAL)
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