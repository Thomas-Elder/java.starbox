package com.starbox.engine.firing.player;

import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.player.Player;

import java.util.List;

public interface PlayerFiringBehavior {
    double getCooldownSeconds();
    double getDurationSeconds();
    GameEventType getShotEventType();
    List<Bullet> createBullets(Player shooter);
}
