package com.starbox.engine.entities.firing.player;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Player;

import java.util.List;

public interface PlayerFiringBehavior {
    double getCooldownSeconds();
    double getDurationSeconds();
    List<Bullet> createBullets(Player shooter);
}
