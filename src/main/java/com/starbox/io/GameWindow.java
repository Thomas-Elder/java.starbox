package com.starbox.io;

import com.starbox.GameConstants;

import javax.swing.JFrame;

public final class GameWindow {

    private GameWindow() {
        // no instances
    }

    public static void launch() {
        JFrame frame = new JFrame(GameConstants.TITLE);
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGame();
    }
}
