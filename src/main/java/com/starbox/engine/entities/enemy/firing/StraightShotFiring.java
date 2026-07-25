package com.starbox.engine.entities.enemy.firing;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;

import java.util.List;

public class StraightShotFiring implements FiringBehavior {
    private final double intervalSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;

    public StraightShotFiring(double intervalSeconds, int bulletSize, int bulletDamage, double bulletSpeed) {
        this.intervalSeconds = intervalSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public double getFireIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Bullet> createBullets(Enemy shooter, Player player) {
        double x = shooter.getX() + shooter.getWidth() / 2.0 - bulletSize / 2.0;
        double y = shooter.getY() + shooter.getHeight();
        return List.of(new Bullet(x, y, bulletSize, bulletDamage, Bullet.Owner.ENEMY, 0, bulletSpeed));
    }
}
