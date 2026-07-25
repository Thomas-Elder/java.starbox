package com.starbox.engine.collision;

import com.starbox.engine.entities.Explosion;

import java.util.List;

public record CollisionResult (int scoreGained, List<Explosion> explosions) {}
