package com.starbox.engine.spawn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpawnSchedule<T> {
    private final List<SpawnEntry<T>> entries = new ArrayList<>();

    public SpawnSchedule<T> at(double atSeconds, T payload){
        entries.add(new SpawnEntry<T>(atSeconds, payload));
        return this;
    }

    public SpawnSchedule<T> repeating(T payload, double start, double interval, int count){
        for (int i = 0; i < count; i++) {
            entries.add(new SpawnEntry<T>(start + i * interval, payload));
        }
        return this;
    }

    public List<SpawnEntry<T>> build() {
        List<SpawnEntry<T>> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparingDouble(SpawnEntry::atSeconds));
        return List.copyOf(sorted);
    }
}
