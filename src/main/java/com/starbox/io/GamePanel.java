package com.starbox.io;

import com.starbox.GameConstants;
import com.starbox.engine.GameEngine;
import com.starbox.engine.GameEvent;
import com.starbox.engine.GameLoop;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 * The AWT side of the game. Owns the drawing surface (via BufferStrategy)
 * and the keyboard listener, and drives a {@link GameEngine} through a
 * {@link GameLoop}. All simulation rules live in GameEngine; all drawing
 * logic lives in {@link GameRenderer}. This class is just the glue between
 * them and the on-screen Canvas -- it deliberately contains no game logic.
 */
public class GamePanel extends Canvas implements GameLoop.Callback {

    private final InputHandler input = new InputHandler();
    private final GameEngine engine = new GameEngine();
    private final GameLoop loop = new GameLoop(this, GameConstants.TARGET_FPS);
    private final AudioManager audio = new AudioManager();

    public GamePanel() {
        setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        setFocusable(true);
        addKeyListener(input);
    }

    public void startGame() {
        createBufferStrategy(3);
        loop.start();
    }

    public void stopGame() {
        loop.stop();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    @Override
    public void update(double deltaSeconds) {
        engine.update(deltaSeconds, input);
    }

    @Override
    public void render() {
        for (GameEvent event : engine.collectEvents()) {
            audio.play(event.type());
        }

        BufferStrategy strategy = getBufferStrategy();
        if (strategy == null) {
            return;
        }

        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        try {
            GameRenderer.render(g, engine);
        } finally {
            g.dispose();
        }
        strategy.show();
    }
}
