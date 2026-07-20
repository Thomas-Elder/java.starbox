package com.starbox;

import com.starbox.io.GameWindow;

import javax.swing.SwingUtilities;

public final class Main {

    private Main() {
        // no instances
    }

    static void main() {
        SwingUtilities.invokeLater(GameWindow::launch);
    }
}
