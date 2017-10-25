package szakdolgozat.istvan.ping_pong;

import android.graphics.Canvas;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameLoop extends Thread {
    static final int FPS = 60;
    private GameView gameView;
    private boolean running = false;
    private GameEngine gameEngine;
    private boolean paused = false;

    public GameLoop(GameView gameView, GameEngine gameEngine)
    {
        this.gameView = gameView;
        this.gameEngine = gameEngine;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            if(!paused && !gameEngine.isGameEnded())
                gameEngine.nextStep();

            try {
                c = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.draw(c);
                }
            } finally {
                if (c != null) {
                    gameView.getHolder().unlockCanvasAndPost(c);
                }
            }
            if(gameEngine.isGameEnded())
            {
                stopLoop();
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stopLoop(){
        this.running = false;
    }

    public void pause() {
        synchronized (gameView.getHolder()) {
            paused = true;
        }
    }

   public void unPause() {
        synchronized (gameView.getHolder()) {
            paused = false;
        }
    }
}