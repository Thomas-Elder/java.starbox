package com.starbox.engine.entities.firing.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Player;

import java.util.List;

public class SingleShotFiring implements PlayerFiringBehavior {
    private final double intervalSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;

    public SingleShotFiring(double intervalSeconds, int bulletSize, int bulletDamage, double bulletSpeed) {
        this.intervalSeconds = intervalSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public double getIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Bullet> createBullets(Player shooter) {
        double x = shooter.getX() + shooter.getWidth() / 2.0 - bulletSize / 2.0;
        double y = shooter.getY();
        return List.of(new Bullet(x, y, bulletSize, bulletDamage, Bullet.Owner.PLAYER, 0, -bulletSpeed));
    }
}
