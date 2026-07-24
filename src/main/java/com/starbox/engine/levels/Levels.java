package com.starbox.engine.levels;

import com.starbox.engine.SpawnSchedule;
import com.starbox.engine.entities.EnemyType;

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
                        new SpawnSchedule()
                                .repeating(EnemyType.NORMAL, 0, 1.2, 25)
                                .repeating(EnemyType.FRIGATE, 5, 5, 5)
                                .at(20, EnemyType.BATTLESHIP)
                                .build()
                ),
                new Level(
                        "BOSSSSSSSSSS",
                        new Color(80, 2, 12),
                        new Color(90, 90, 110),
                        new Color(150, 150, 180),
                        new Color(220, 220, 255),
                        30,
                        new SpawnSchedule()
                                .at(0, EnemyType.BOSS)
                                .build()
                ),
                new Level(
                        "Sector 2: Crimson Nebula",
                        new Color(18, 2, 8),
                        new Color(110, 55, 65),
                        new Color(180, 85, 95),
                        new Color(255, 150, 160),
                        30,
                        new SpawnSchedule()
                                .repeating(EnemyType.NORMAL, 0, 1.2, 12)
                                .repeating(EnemyType.FRIGATE, 5, 3, 5)
                                .at(15, EnemyType.BATTLESHIP)
                                .at(20, EnemyType.BOSS)
                                .build()
                ),
                new Level(
                        "Sector 3: Ion Storm",
                        new Color(0, 10, 16),
                        new Color(55, 100, 110),
                        new Color(90, 170, 180),
                        new Color(150, 240, 255),
                        35,
                        new SpawnSchedule()
                                .repeating(EnemyType.NORMAL, 0, 1.2, 12)
                                .repeating(EnemyType.FRIGATE, 5, 3, 5)
                                .at(15, EnemyType.BATTLESHIP)
                                .at(20, EnemyType.BOSS)
                                .build()
                )
        );
    }
}
