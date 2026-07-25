package com.starbox.engine.spawn;

import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.enemy.EnemyType;

import java.util.List;

public class FighterSpawnBehavior implements SpawnBehavior {
    private final double intervalSeconds;
    private final EnemyType spawnType;

    public FighterSpawnBehavior(double intervalSeconds, EnemyType spawnType){
        this.intervalSeconds = intervalSeconds;
        this.spawnType = spawnType;
    }

    @Override
    public double getSpawnIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Enemy> createSpawns(Enemy spawner) {
        double y = spawner.getY() + spawner.getHeight();
        double leftX = spawner.getX() - spawnType.getSize() - 4;
        double rightX = spawner.getX() + spawner.getWidth() + 4;
        return List.of(
                new Enemy(leftX, y, spawnType),
                new Enemy(rightX, y, spawnType));
    }
}
