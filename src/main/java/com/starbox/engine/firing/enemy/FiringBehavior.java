package com.starbox.engine.firing.enemy;

import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;

import java.util.List;

public interface FiringBehavior {
    double getFireIntervalSeconds();
    GameEventType getShotEventType();
    List<Bullet> createBullets(Enemy shooter, Player player);
}
