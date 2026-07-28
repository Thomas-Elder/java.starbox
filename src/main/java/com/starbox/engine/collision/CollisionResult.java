package com.starbox.engine.collision;

import com.starbox.engine.GameEvent;

import java.util.List;

public record CollisionResult (int scoreGained, List<GameEvent> events) {}
