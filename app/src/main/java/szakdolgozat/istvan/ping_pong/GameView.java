package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Pisti on 2017. 03. 22..
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private int width, height;
    private GameLoop gameLoop;
    private GameEngine gameEngine;
    private Boolean multiplayer;
    private Player[] players;
    private GameActivity gameActivity;
    private TextView playerScore, playerScore2;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        this.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        gameEngine = new GameEngine(width, height, Difficulty.MEDIUM);
        this.multiplayer = false;
        gameLoop = new GameLoop(this, gameEngine);
        players = new Player[2];
        setFocusable(true);
        gameActivity = (GameActivity) getContext();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View root = getRootView();
        playerScore = (TextView) root.findViewById(R.id.playerScore);
        playerScore2 = (TextView) root.findViewById(R.id.playerScore2);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                playerScore.setText(String.valueOf(gameEngine.getGameState().getPlayer1().getScore()));
                playerScore2.setText(String.valueOf(gameEngine.getGameState().getPlayer2().getScore()));
            }

        });
        Paint paint = new Paint();
        Ball ball = gameEngine.getGameState().getBall();
        Player player = gameEngine.getGameState().getPlayer1();
        paint.setColor(Color.BLUE);
        canvas.drawColor(Color.WHITE);
        paint.setColor(ball.getColor());
        canvas.drawCircle((float) ball.getX(), (float) ball.getY(), (int) ball.getDiameter(), paint);
        paint.setColor(player.getColor());
        canvas.drawRect((float) (player.getX() - player.getWidth() / 2), (float) (player.getY() - player.getHeight() / 2), (float) (player.getX() + player.getWidth() / 2), (float) (player.getY() + player.getHeight() / 2), paint);
        player = gameEngine.getGameState().getPlayer2();
        paint.setColor(player.getColor());
        canvas.drawRect((float) (player.getX() - player.getWidth() / 2), (float) (player.getY() - player.getHeight() / 2), (float) (player.getX() + player.getWidth() / 2), (float) (player.getY() + player.getHeight() / 2), paint);
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

/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            if (event.getY(i) > height - (height * 1 / 4))
                gameEngine.movePlayer1(event.getX(i), event.getY(i), gameEngine.getGameState().getPlayer1());
            else if (multiplayer && event.getY(i) < height * 1 / 4)
                gameEngine.movePlayer2(event.getX(i), event.getY(i), gameEngine.getGameState().getPlayer2());
        }
        return true;
    }*/

    //-------------------------------------------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers
                if (event.getY(pointerIndex) > height/2) {
                    players[pointerIndex] = gameEngine.getGameState().getPlayer1();
                    players[pointerIndex].setMoving(true);
                    gameEngine.movePlayer1(event.getX(pointerIndex), event.getY(pointerIndex), players[pointerIndex]);
                } else {
                    players[pointerIndex] = gameEngine.getGameState().getPlayer2();
                    players[pointerIndex].setMoving(true);
                    gameEngine.movePlayer2(event.getX(pointerIndex), event.getY(pointerIndex), players[pointerIndex]);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    if (event.getY(i) > height/2) {
                        gameEngine.movePlayer1(event.getX(i), event.getY(i), players[pointerIndex]);
                    } else {
                        gameEngine.movePlayer2(event.getX(i), event.getY(i), players[pointerIndex]);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                players[pointerIndex].setMoving(false);
                //FINGER UP, PLAYER NOT MOVING, DEFAULT HIT WILL BE EXECUTED
                break;
            }
        }
        invalidate();

        return true;
    }

    //-------------------------------------------------------------------------------
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

