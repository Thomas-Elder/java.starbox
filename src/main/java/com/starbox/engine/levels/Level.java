package com.starbox.engine.levels;

import com.starbox.engine.entities.PowerupType;
import com.starbox.engine.spawn.SpawnEntry;
import com.starbox.engine.entities.enemy.EnemyType;

import java.awt.Color;
import java.util.List;

public record Level(
        String name,
        LevelType levelType,
        Color backgroundColor,
        Color farStarColor,
        Color midStarColor,
        Color nearStarColor,
        double durationSeconds,
        List<SpawnEntry<EnemyType>> enemySchedule,
        List<SpawnEntry<PowerupType>> powerupSchedule,
        EnemyType bossType
) {

    /**
     * Constructs a normal level
     * @param name level name
     * @param backgroundColor background color
     * @param farStarColor color of distant stars
     * @param midStarColor color of midground stars
     * @param nearStarColor color of near stars
     * @param durationSeconds duration of the level
     * @param enemySchedule the spawn schedule for enemies for the level
     * @param powerupSchedule the spawn schedule for enemies for the level
     * @return a configured Level
     */
    public static Level normal(String name,
                               Color backgroundColor,
                               Color farStarColor,
                               Color midStarColor,
                               Color nearStarColor,
                               double durationSeconds,
                               List<SpawnEntry<EnemyType>> enemySchedule,
                               List<SpawnEntry<PowerupType>> powerupSchedule){
        return new Level(name, LevelType.NORMAL, backgroundColor, farStarColor,
                midStarColor, nearStarColor, durationSeconds, enemySchedule, powerupSchedule, null);
    }

    /**
     * Constructs a boss level
     * @param name level name
     * @param backgroundColor background color
     * @param farStarColor color of distant stars
     * @param midStarColor color of midground stars
     * @param nearStarColor color of near stars
     * @param bossType the EnemyType of the boss
     * @return a configured Level
     */
    public static Level boss(String name,
                             Color backgroundColor,
                             Color farStarColor,
                             Color midStarColor,
                             Color nearStarColor,
                             List<SpawnEntry<EnemyType>> enemySchedule,
                             List<SpawnEntry<PowerupType>> powerupSchedule,
                             EnemyType bossType) {
        return new Level(name, LevelType.BOSS, backgroundColor, farStarColor,
                midStarColor, nearStarColor, 0, enemySchedule, powerupSchedule, bossType);
    }
}
