package com.starbox.engine.levels;

import java.util.List;

/**
 * Tracks which level is active and how far into it the player is. A level
 * is considered "complete" once its duration has elapsed while the player
 * is still alive -- GamePanel decides what that means for the game
 * state (advance to the next level, or victory on the last one).
 */
public class LevelManager {

    private final List<Level> levels;
    private int index = 0;
    private double elapsedSeconds = 0;

    public LevelManager(List<Level> levels) {
        if (levels.isEmpty()) {
            throw new IllegalArgumentException("A campaign needs at least one level");
        }
        this.levels = levels;
    }

    public Level current() {
        return levels.get(index);
    }

    public int levelNumber() {
        return index + 1;
    }

    public int totalLevels() {
        return levels.size();
    }

    public boolean isFinalLevel() {
        return index == levels.size() - 1;
    }

    public void tick(double deltaSeconds) {
        elapsedSeconds += deltaSeconds;
    }

    public boolean isComplete() {
        return elapsedSeconds >= current().durationSeconds();
    }

    /** 0.0 to 1.0 progress through the current level's duration. */
    public double progress() {
        return Math.min(1.0, elapsedSeconds / current().durationSeconds());
    }

    /**
     * Moves to the next level if one exists.
     *
     * @return false if the campaign was already on its final level.
     */
    public boolean advance() {
        if (isFinalLevel()) {
            return false;
        }
        index++;
        elapsedSeconds = 0;
        return true;
    }

    public void reset() {
        index = 0;
        elapsedSeconds = 0;
    }
}
