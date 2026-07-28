package com.starbox.engine.entities.player.firing;

import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.Bullet;

import java.util.List;

public record FiredShot(GameEventType eventType, List<Bullet> bullets) {}
