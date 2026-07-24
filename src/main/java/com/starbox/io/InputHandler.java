package com.starbox.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks which keys are currently held down, and exposes convenient
 * game-facing queries (movement + shooting) with both arrow keys and WASD.
 * Two flavors of query are available:
 * - {@code isXxxPressed()} — true for as long as the key is held. Good for
 *   continuous things like movement and rapid fire.
 * - {@code isShootJustPressed()} — true for exactly one update tick per
 *   physical key press. Good for menu-style actions (advance a screen,
 *   restart) where holding the key down shouldn't repeat the action.
 * This is the one class that actually knows about AWT key events; so the
 * engine package can consume input without depending on AWT at all.
 */
public class InputHandler implements KeyListener {

    // ConcurrentHashMap-backed sets: keyPressed/keyReleased fire on the AWT
    // event thread, while these are read from the game loop thread.
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();
    private final Set<Integer> justPressedKeys = ConcurrentHashMap.newKeySet();

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Set.add() returns true only the first time -- this ignores the
        // repeated keyPressed events the OS sends while a key is held.
        if (pressedKeys.add(code)) {
            justPressedKeys.add(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        pressedKeys.remove(code);
        justPressedKeys.remove(code);
    }

    public boolean isDown(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /** Consumes and returns whether the given key had a fresh press since it was last checked. */
    public boolean consumeJustPressed(int keyCode) {
        return justPressedKeys.remove(keyCode);
    }

    public boolean isUpPressed() {
        return isDown(KeyEvent.VK_UP) || isDown(KeyEvent.VK_W);
    }

    public boolean isDownPressed() {
        return isDown(KeyEvent.VK_DOWN) || isDown(KeyEvent.VK_S);
    }

    public boolean isLeftPressed() {
        return isDown(KeyEvent.VK_LEFT) || isDown(KeyEvent.VK_A);
    }

    public boolean isRightPressed() {
        return isDown(KeyEvent.VK_RIGHT) || isDown(KeyEvent.VK_D);
    }

    public boolean isShootPressed() {
        return isDown(KeyEvent.VK_SPACE);
    }

    public boolean isShootJustPressed() {
        return consumeJustPressed(KeyEvent.VK_SPACE);
    }
    public boolean isEJustPressed() {
        return consumeJustPressed(KeyEvent.VK_E);
    }
}
