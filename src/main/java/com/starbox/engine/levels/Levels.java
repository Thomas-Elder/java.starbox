package com.starbox.engine.levels;

import java.awt.Color;
import java.util.List;

/**
 * Defines the actual campaign: an ordered list of {@link Level}s.
 */
public final class Levels {

    private Levels() {
        // no instances
    }

    public static List<Level> campaign() {
        return List.of(
                new Level(
                        "Sector 1: Deep Space",
                        new Color(2, 2, 12),
                        new Color(90, 90, 110),
                        new Color(150, 150, 180),
                        new Color(220, 220, 255),
                        30,
                        1.2
                ),
                new Level(
                        "Sector 2: Crimson Nebula",
                        new Color(18, 2, 8),
                        new Color(110, 55, 65),
                        new Color(180, 85, 95),
                        new Color(255, 150, 160),
                        30,
                        1.0
                ),
                new Level(
                        "Sector 3: Ion Storm",
                        new Color(0, 10, 16),
                        new Color(55, 100, 110),
                        new Color(90, 170, 180),
                        new Color(150, 240, 255),
                        35,
                        0.8
                )
        );
    }
}
