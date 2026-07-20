package com.starbox.engine;

/**
 * The high-level state machine for the whole game. GameEngine switches its
 * update behaviour, and GameRenderer its drawing, based on which state is
 * active.
 */
public enum GameState {

    /** Brief "LEVEL X: Name" banner shown before a level starts. */
    LEVEL_INTRO,

    /** Normal gameplay: movement, shooting, spawning, collisions. */
    PLAYING,

    /** Shown after surviving a level's duration, before the next one. */
    LEVEL_COMPLETE,

    /** Player ran out of lives. */
    GAME_OVER,

    /** Final level's LEVEL_COMPLETE, i.e. the whole campaign is done. */
    VICTORY
}
