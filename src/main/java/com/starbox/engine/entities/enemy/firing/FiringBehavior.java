package com.starbox.engine.entities.enemy.firing;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;

import java.util.List;

public interface FiringBehavior {
    double getFireIntervalSeconds();
    List<Bullet> createBullets(Enemy shooter, Player player);
}
