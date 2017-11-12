package szakdolgozat.istvan.ping_pong;


import android.graphics.Color;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameState {
    private Ball ball;
    private Player player1, player2;

    public GameState(double screenWidth, double screenHeight) {
        ball = new Ball((int) (screenHeight + screenWidth) / 90, screenWidth / 2, screenHeight / 2, Color.WHITE);
        ball.generateNewDirection();
        player1 = new Player(screenWidth / 2, screenHeight - screenHeight / 8, screenWidth / 5, screenHeight / 40, Color.BLUE);
        player2 = new Player(screenWidth / 2, screenHeight / 8, screenWidth / 5, screenHeight / 40, Color.RED);
    }


    public GameState(Ball ball, Player player1, Player player2) {
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
