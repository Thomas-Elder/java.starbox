package com.starbox.engine.entities.firing.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class SpreadShotFiring implements PlayerFiringBehavior {
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
    public double getIntervalSeconds() { return intervalSeconds; }

    @Override
    public List<Bullet> createBullets(Player shooter){
        List<Bullet> bullets = new ArrayList<>();

        return bullets;
    }
}
