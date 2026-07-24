package com.starbox.engine.entities.firing.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class MultiShotFiring implements PlayerFiringBehavior{
    private final double intervalSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;
    private final int bulletCount;

    public MultiShotFiring(double intervalSeconds, int bulletSize, int bulletDamage, double bulletSpeed, int bulletCount) {
        this.intervalSeconds = intervalSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
        this.bulletCount = bulletCount;
    }

    @Override
    public double getIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Bullet> createBullets(Player shooter) {
        List<Bullet> bullets = new ArrayList<>();

        double x = shooter.getX(); //+ shooter.getWidth() / 2.0 - bulletSize / 2.0;
        double y = shooter.getY();

        // So if count is 5 and width is 28... but the width of the bullets....
        double step = bulletCount > 1 ? (double) shooter.getWidth() / (bulletCount - 1) : 0;

        for (int i = 0; i < bulletCount; i++) {
            if (i == 0) {
                bullets.add(new Bullet(x - bulletSize / 2.0, y, bulletSize, bulletDamage, Bullet.Owner.PLAYER, 0, -bulletSpeed));
            } else {
                double dx = x - bulletSize / 2.0 + (step) * i;
                bullets.add(new Bullet(dx, y, bulletSize, bulletDamage, Bullet.Owner.PLAYER, 0, -bulletSpeed));
            }
        }

        return bullets;
    }
}
