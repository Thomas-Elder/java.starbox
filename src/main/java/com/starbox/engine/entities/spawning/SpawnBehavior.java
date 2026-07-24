package com.starbox.engine.entities.spawning;

import com.starbox.engine.entities.Enemy;

import java.util.List;

public interface SpawnBehavior {
    double getSpawnIntervalSeconds();
    List<Enemy> createSpawns(Enemy spawner);
}
