package com.starbox.engine.levels;

import com.starbox.engine.SpawnEntry;

import java.awt.Color;
import java.util.List;

public record Level(
        String name,
        Color backgroundColor,
        Color farStarColor,
        Color midStarColor,
        Color nearStarColor,
        double durationSeconds,
        List<SpawnEntry> spawnSchedule
) {
}
