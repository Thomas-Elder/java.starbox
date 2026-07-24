package com.starbox.engine.entities.firing.enemy;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Enemy;
import com.starbox.engine.entities.Player;

import java.util.List;

public interface FiringBehavior {
    double getFireIntervalSeconds();
    List<Bullet> createBullets(Enemy shooter, Player player);
}
