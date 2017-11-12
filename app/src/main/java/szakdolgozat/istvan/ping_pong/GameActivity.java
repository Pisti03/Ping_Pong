package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;


public class GameActivity extends Activity {
    private GameView gameView;
    private boolean paused = false;
    private PopupWindow popupWindow;
    private int players, difficulty;
    private int bestOf;
    private String player1, player2;
    private Boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        Options options = new Options(this);
        sound = options.getSound();
        if(b != null) {
            players = b.getInt("players");
            difficulty = b.getInt("difficulty");
            bestOf = b.getInt("bestof");
            player1 = b.getString("player1");
            player2 = b.getString("player2");
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        inflater.inflate(R.layout.activity_game, (ViewGroup) findViewById(R.id.mainlayout));
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.gameView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(players == 1)
            gameView.startSinglePlayer(difficulty, bestOf, player1, player2);
        else if(players == 2)
            gameView.startMultiPlayer(bestOf, player1, player2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.continueGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!paused) {
                gameView.pauseGame();
                paused = true;
                callPopUp();
            }
            return true;
        }
        return false;
    }

    private void callPopUp(){

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.pausepopup, null);

        Button buttonContinue = (Button) popupView.findViewById(R.id.buttonContinue);

        if(!gameView.isGameEnded()) {
            buttonContinue.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    popupWindow.dismiss();
                    gameView.continueGame();
                    paused=false;
                }
            });
        }

        Button buttonRestart = (Button) popupView.findViewById(R.id.buttonRestart);
        buttonRestart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                gameView.restart();
                paused=false;
            }
        });

        final Button buttonSound = (Button) popupView.findViewById(R.id.buttonSound);

        if(sound)
            buttonSound.setText("MUTE");
        else
            buttonSound.setText("UNMUTE");

        buttonSound.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               if(sound) {
                   sound = false;
                   buttonSound.setText("UNMUTE");
                   gameView.muteGame();
               } else {
                   sound = true;
                   buttonSound.setText("MUTE");
                   gameView.unMuteGame();
               }
            }
        });

        Button buttonExit = (Button) popupView.findViewById(R.id.buttonExit2);
        buttonExit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                gameView.stopGame();
                finish();
            }
        });

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gameView.continueGame();
                paused = false;
            }
        });
    }
}
