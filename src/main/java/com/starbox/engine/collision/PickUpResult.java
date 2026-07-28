package com.starbox.engine.collision;

import com.starbox.engine.entities.player.Powerup;

import java.util.List;

public record PickUpResult(List<Powerup> powerUps) {
}
