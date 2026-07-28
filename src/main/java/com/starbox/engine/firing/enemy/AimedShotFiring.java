package com.starbox.engine.firing.enemy;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;

import java.util.List;

public class AimedShotFiring implements FiringBehavior {
    private final double intervalSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;

    public AimedShotFiring(double intervalSeconds, int bulletSize, int bulletDamage, double bulletSpeed) {
        this.intervalSeconds = intervalSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public double getFireIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Bullet> createBullets(Enemy shooter, Player player) {
        double startX = shooter.getX() + shooter.getWidth() / 2.0;
        double startY = shooter.getY() + shooter.getHeight();
        double targetX = player.getX() + player.getWidth() / 2.0;
        double targetY = player.getY() + player.getHeight() / 2.0;

        double dx = targetX - startX;
        double dy = targetY - startY;
        double distance = Math.hypot(dx, dy);

        double vx, vy;

        vx = (dx / distance) * bulletSpeed;
        vy = (dy / distance) * bulletSpeed;

        return List.of(new Bullet(
                startX - bulletSize / 2.0,
                startY,
                bulletSize,
                bulletDamage,
                Bullet.Owner.ENEMY,
                vx,
                vy
        ));
    }
}
