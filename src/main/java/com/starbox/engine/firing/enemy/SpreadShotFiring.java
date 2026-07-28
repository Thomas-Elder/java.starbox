package com.starbox.engine.firing.enemy;

import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SpreadShotFiring implements FiringBehavior {
    private final double intervalSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;
    private final double bulletCount;
    private final double spreadDegrees;

    public SpreadShotFiring(double intervalSeconds, int bulletSize, int bulletDamage, double bulletSpeed, double bulletCount, double spreadDegrees){
        this.intervalSeconds = intervalSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
        this.bulletCount = bulletCount;
        this.spreadDegrees = spreadDegrees;
    }

    @Override
    public double getFireIntervalSeconds() { return intervalSeconds; }

    @Override
    public GameEventType getShotEventType() { return GameEventType.ENEMY_SHOT_SPREAD; }

    @Override
    public List<Bullet> createBullets(Enemy shooter, Player player){
        double centerX = shooter.getX() + shooter.getWidth() / 2.0;
        double centerY = shooter.getY() + shooter.getHeight();

        List<Bullet> bullets = new ArrayList<>();

        double startAngle = 90 - spreadDegrees / 2.0;
        double step = bulletCount > 1 ? spreadDegrees / (bulletCount - 1) : 0;

        for (int i = 0; i < bulletCount; i++) {
            double angle = Math.toRadians(startAngle + step * i);
            double vx = Math.cos(angle) * bulletSpeed;
            double vy = Math.sin(angle) * bulletSpeed;

            bullets.add(new Bullet(
                    centerX - bulletSize / 2.0,
                    centerY,
                    bulletSize,
                    bulletDamage,
                    Bullet.Owner.ENEMY,
                    vx,
                    vy));
        }

        return bullets;
    }
}
