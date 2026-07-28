package com.starbox.engine.firing.player;

import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SpreadShotFiring implements PlayerFiringBehavior {
    private final double cooldownSeconds;
    private final double durationSeconds;
    private final int bulletSize;
    private final int bulletDamage;
    private final double bulletSpeed;
    private final double bulletCount;
    private final double spreadDegrees;

    public SpreadShotFiring(double cooldownSeconds, double durationSeconds, int bulletSize, int bulletDamage, double bulletSpeed, double bulletCount, double spreadDegrees){
        this.cooldownSeconds = cooldownSeconds;
        this.durationSeconds = durationSeconds;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
        this.bulletCount = bulletCount;
        this.spreadDegrees = spreadDegrees;
    }

    @Override
    public double getCooldownSeconds() { return cooldownSeconds; }

    @Override
    public double getDurationSeconds() { return durationSeconds; }

    @Override
    public GameEventType getShotEventType() { return GameEventType.PLAYER_SHOT_SPREAD; }

    @Override
    public List<Bullet> createBullets(Player shooter){
        double centerX = shooter.getX() + shooter.getWidth() / 2.0;
        double centerY = shooter.getY();

        List<Bullet> bullets = new ArrayList<>();

        // 270 deg is straight up.
        double startAngle = 270 - spreadDegrees / 2.0;
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
                    Bullet.Owner.PLAYER,
                    vx,
                    vy));
        }

        return bullets;
    }
}
