package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import szakdolgozat.istvan.ping_pong.R;

/**
 * Created by Pisti on 2017. 03. 22..
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private int width, height;
    private GameLoop gameLoop;
    private GameEngine gameEngine;
    private Boolean multiplayer = true;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        this.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        gameEngine = new GameEngine(width, height);//, Difficulty.EASY);
        gameLoop = new GameLoop(this, gameEngine);
        setFocusable(true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        Ball ball = gameEngine.getGameState().getBall();
        Player player = gameEngine.getGameState().getPlayer1();
        paint.setColor(Color.BLUE);
        canvas.drawColor(Color.WHITE);
        paint.setColor(ball.getColor());
        canvas.drawCircle((float) ball.getX(), (float) ball.getY(), (int) ball.getDiameter(), paint);
        paint.setColor(player.getColor());
        canvas.drawRect((float) (player.getPosX() - player.getWidth() / 2), (float) (player.getPosY() - player.getHeight() / 2), (float) (player.getPosX() + player.getWidth() / 2), (float) (player.getPosY() + player.getHeight() / 2), paint);
        player = gameEngine.getGameState().getPlayer2();
        paint.setColor(player.getColor());
        canvas.drawRect((float) (player.getPosX() - player.getWidth() / 2), (float) (player.getPosY() - player.getHeight() / 2), (float) (player.getPosX() + player.getWidth() / 2), (float) (player.getPosY() + player.getHeight() / 2), paint);
        paint.setAlpha(50);
        paint.setColor(Color.GRAY);
        canvas.drawLine(0, height / 2, width, height / 2, paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(0, height - (height * 1 / 4), width, height - (height * 1 / 4), paint);
        canvas.drawLine(0, height - (height * 1 / 12), width, height - (height * 1 / 12), paint);
        paint.setColor(Color.RED);
        canvas.drawLine(0, height * 1 / 4, width, height * 1 / 4, paint);
        canvas.drawLine(0, height * 1 / 12, width, height * 1 / 12, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println(w + " " + h);
        this.width = w;
        this.height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameLoop.setRunning(false);
        while (retry) {
            try {
                gameLoop.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            if (event.getY(i) > height - (height * 1 / 4))
                gameEngine.movePlayer1(event.getX(i), event.getY(i), gameEngine.getGameState().getPlayer1());
            else if (multiplayer && event.getY(i) < height * 1 / 4)
                gameEngine.movePlayer2(event.getX(i), event.getY(i), gameEngine.getGameState().getPlayer2());
        }
        /*double x = (double) event.getX();
        double y = (double) event.getY();
        gameEngine.movePlayer1(x, y, gameEngine.getGameState().getPlayer1());
        */
        return true;
    }

    public void restart() {
        gameEngine.restart();
        continueGame();

    }

    public void pauseGame() {
        gameLoop.pause();
    }

    public void continueGame() {
        gameLoop.unPause();
    }
}

