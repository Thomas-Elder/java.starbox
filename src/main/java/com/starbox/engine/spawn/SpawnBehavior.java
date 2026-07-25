package com.starbox.engine.spawn;

import com.starbox.engine.entities.enemy.Enemy;

import java.util.List;

public interface SpawnBehavior {
    double getSpawnIntervalSeconds();
    List<Enemy> createSpawns(Enemy spawner);
}
