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
    private Bitmap bmp;
    private int kepX, kepY;
    private int signX = 1, signY = 1;
    private int width, height;
    private GameLoop gameLoop;
    private GameEngine gameEngine;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ball);
        holder = getHolder();
        holder.addCallback(this);
        this.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        gameEngine = new GameEngine(width, height);
        gameLoop = new GameLoop(this, gameEngine);
        setFocusable(true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawColor(Color.WHITE);
        Ball temp = gameEngine.getGameState().getBall();
        paint.setColor(temp.getColor());
        canvas.drawCircle((float) temp.getX(), (float) temp.getY(), (int) temp.getDiameter(), paint);
        Player temp2 = gameEngine.getGameState().getPlayer1();
        paint.setColor(temp2.getColor());
        canvas.drawRect((float) (temp2.getPosX() - temp2.getWidth() / 2), (float) (temp2.getPosY() - temp2.getHeight() / 2), (float) (temp2.getPosX() + temp2.getWidth() / 2), (float) (temp2.getPosY() + temp2.getHeight() / 2), paint);
        temp2 = gameEngine.getGameState().getPlayer2();
        paint.setColor(temp2.getColor());
        canvas.drawRect((float) (temp2.getPosX() - temp2.getWidth() / 2), (float) (temp2.getPosY() - temp2.getHeight() / 2), (float) (temp2.getPosX() + temp2.getWidth() / 2), (float) (temp2.getPosY() + temp2.getHeight() / 2), paint);
        paint.setAlpha(50); //Put a value between 0 and 255
        paint.setColor(Color.GRAY); //Put your line color
        canvas.drawLine(0, height/2, width, height/2, paint);
    }


    public void rajzol() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(this.bmp, kepX - (bmp.getWidth() / 2), kepY - (bmp.getHeight() / 2), null);
        holder.unlockCanvasAndPost(canvas);
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

    public boolean onTouchEvent(MotionEvent event) {
        double x = (double) event.getX();
        double y = (double) event.getY();
        gameEngine.movePlayer1(x,y);
        return true;
    }

    public void restart()
    {
        gameEngine.restart();
        continueGame();

    }

    public void pauseGame(){
        gameLoop.setRunning(false);
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

    public void continueGame()
    {
        gameLoop = new GameLoop(this, gameEngine);
        gameLoop.setRunning(true);
        gameLoop.start();
    }
}

