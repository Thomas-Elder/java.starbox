package com.starbox.engine;

public class GameLoop implements Runnable {

    public interface Callback {
        void update(double deltaSeconds);
        void render();
    }

    private final Callback callback;
    private final int targetFps;

    private Thread thread;
    private volatile boolean running = false;

    public GameLoop(Callback callback, int targetFps) {
        this.callback = callback;
        this.targetFps = targetFps;
    }

    public void start() {
        if (thread != null) {
            return;
        }
        running = true;
        thread = new Thread(this, "game-loop");
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {

        final double fixedDelta = 1.0 / targetFps; // for 60fps, 0.01667 seconds

        while (running) {
            long now = System.nanoTime();

            callback.update(fixedDelta);
            callback.render();

            /* frameEnd - now is how long this iteration's update()+render() actually took to run.
             * fixedDelta - is the "budget" for one frame (16.67ms).
             * So sleepNanos is whatever's left of that budget. */
            long frameEnd = System.nanoTime();
            long sleepNanos = (long) (fixedDelta * 1_000_000_000) - (frameEnd - now);

            // Sleep until it's time to generate another frame.
            if (sleepNanos > 0) {
                try {
                    Thread.sleep(sleepNanos / 1_000_000, (int) (sleepNanos % 1_000_000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
