package com.starbox.engine;

import com.starbox.engine.entities.Explosion;

import java.util.List;

public record CollisionResult (int scoreGained, List<Explosion> explosions) {}
