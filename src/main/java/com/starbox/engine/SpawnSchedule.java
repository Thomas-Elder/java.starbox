package com.starbox.engine;

import com.starbox.engine.entities.EnemyType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpawnSchedule {
    private final List<SpawnEntry> entries = new ArrayList<>();

    public SpawnSchedule at(double atSeconds, EnemyType type){
        entries.add(new SpawnEntry(atSeconds, type));
        return this;
    }

    public SpawnSchedule repeating(EnemyType type, double start, double interval, int count){
        for (int i = 0; i < count; i++) {
            entries.add(new SpawnEntry(start + i * interval, type));
        }
        return this;
    }

    public List<SpawnEntry> build() {
        List<SpawnEntry> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparingDouble(SpawnEntry::atSeconds));
        return List.copyOf(sorted);
    }
}
