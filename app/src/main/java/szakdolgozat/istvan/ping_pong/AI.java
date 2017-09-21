package szakdolgozat.istvan.ping_pong;

import static szakdolgozat.istvan.ping_pong.Difficulty.*;

/**
 * Created by Pisti on 2017. 09. 19..
 */

public class AI {
    private static double DIF1_SPEED = 10;
    private static double DIF2_SPEED = 10;
    private static double DIF3_SPEED = 15;
    private Difficulty difficulty;
    private double speed;
    private boolean moveOnY;

    public AI(Difficulty difficulty) {
        this.difficulty = difficulty;
        switch(difficulty){
            case EASY:
                speed = DIF1_SPEED;
                moveOnY = false;
                break;
            case MEDIUM:
                speed = DIF2_SPEED;
                moveOnY = true;
                break;
            case HARD:
                speed = DIF3_SPEED;
                break;
            default:
                speed = DIF2_SPEED;
                moveOnY = true;
                break;
        }
    }

    public Point getDestination(GameState gamesState){
        Point point = new Point();
        Ball ball = gamesState.getBall();
        Player opponent = gamesState.getPlayer1();
        Player ai = gamesState.getPlayer2();
        if(ball.getX() > ai.getPosX() + speed)
            point.setX(ai.getPosX() + speed);
        else if(ball.getX() < ai.getPosX() - speed)
            point.setX(ai.getPosX() - speed);
        else
            point.setX(ball.getX());
    return point;
    }

}
