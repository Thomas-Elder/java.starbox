package com.starbox.engine.levels;

import java.awt.Color;

public record Level(
        String name,
        Color backgroundColor,
        Color farStarColor,
        Color midStarColor,
        Color nearStarColor,
        double durationSeconds,
        double baseEnemySpawnInterval
) {
}
