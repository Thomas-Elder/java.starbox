package com.starbox.io;

import com.starbox.engine.GameEvent;
import com.starbox.engine.GameEventType;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class AudioManager {

    private final Map<GameEventType, Clip[]> clipPools = new EnumMap<>(GameEventType.class);
    private final Map<GameEventType, Integer> nextClipIndex = new EnumMap<>(GameEventType.class);

    public AudioManager() {
        load(GameEventType.ENEMY_HIT, "/sounds/enemy_hit.wav", 6);
        load(GameEventType.ENEMY_KILLED, "/sounds/enemy_killed.wav", 6);

        load(GameEventType.PLAYER_HIT, "/sounds/player_hit.wav", 4);
        load(GameEventType.PLAYER_SHOT_SINGLE, "/sounds/player_shot.wav", 6);
        load(GameEventType.PLAYER_SHOT_MULTI, "/sounds/player_shot.wav", 6);
        load(GameEventType.PLAYER_SHOT_RAPID, "/sounds/player_shot.wav", 6);
        load(GameEventType.PLAYER_SHOT_SPREAD, "/sounds/player_split_shot.wav", 4);
    }

    private void load(GameEventType event, String resourcePath, int poolSize) {

        try {
            Clip[] pool = new Clip[poolSize];
            for (int i = 0; i < poolSize; i++) {
                pool[i] = createClip(resourcePath);
            }

            clipPools.put(event, pool);
            nextClipIndex.put(event, 0);
        } catch (Exception e) {
            System.err.println("Could not load sound for " + event + ": " + e.getMessage());
        }
    }

    private Clip createClip(String resourcePath) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        URL url = getClass().getResource(resourcePath);

        if (url == null){
            throw new IOException("Missing resource: " + resourcePath);
        }

        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(url)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        }
    }

    public void play(GameEventType event) {
        Clip [] pool = clipPools.get(event);
        if(pool == null) return; // if this game event type doesn't have a sound

        int index = nextClipIndex.get(event);
        Clip clip = pool[index];

        clip.stop();
        clip.setFramePosition(0);
        clip.start();

        nextClipIndex.put(event, (index + 1) % pool.length);
    }
}
