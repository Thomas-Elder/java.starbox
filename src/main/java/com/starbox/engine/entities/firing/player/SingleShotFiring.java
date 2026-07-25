package com.starbox.engine.entities.firing.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Player;

import java.util.List;

public class SingleShotFiring implements PlayerFiringBehavior {
    private final double cooldownSeconds;
    private final double durationSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;

    public SingleShotFiring(double cooldownSeconds, double durationSeconds, int bulletSize, int bulletDamage, double bulletSpeed) {
        this.cooldownSeconds = cooldownSeconds;
        this.durationSeconds = durationSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public double getCooldownSeconds() { return cooldownSeconds; }

    @Override
    public double getDurationSeconds() { return durationSeconds; }

    @Override
    public List<Bullet> createBullets(Player shooter) {
        double x = shooter.getX() + shooter.getWidth() / 2.0 - bulletSize / 2.0;
        double y = shooter.getY();
        return List.of(new Bullet(x, y, bulletSize, bulletDamage, Bullet.Owner.PLAYER, 0, -bulletSpeed));
    }
}
