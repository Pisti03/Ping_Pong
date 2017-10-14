package szakdolgozat.istvan.ping_pong;

import java.util.Random;

/**
 * Created by Pisti on 2017. 09. 19..
 */

public class AI {
    private static double EASY_SPEED = 10;
    private static double MEDIUM_SPEED = 6.25;
    private static double HARD_SPEED = 20;
    private Difficulty difficulty;
    private double minY, maxY, hitY;
    private boolean willHit, ballInArea;
    private Direction hitDirection;

    public AI(Difficulty difficulty, double minY, double maxY) {
        this.difficulty = difficulty;
        this.minY = minY;
        this.maxY = maxY;
        setHitY();
        setWillHit();
        this.ballInArea = false;
    }

    private void setHitY() {
        switch (difficulty) {
            case EASY:
                this.hitY = maxY - (EASY_SPEED / 2);
                break;
            case MEDIUM:
                this.hitY = maxY - (MEDIUM_SPEED / 2);
                break;
            case HARD:
                this.hitY = maxY - (HARD_SPEED / 2);
                break;
        }
    }

    public Point getDestination(GameState gamesState) {
        Ball ball = gamesState.getBall();
        Player opponent = gamesState.getPlayer1();
        Player ai = gamesState.getPlayer2();

        if(!ballInArea)
            if(ball.getY() < maxY){
                ballInArea=!ballInArea;
                setWillHit();
                if(difficulty == Difficulty.HARD)
                    generateHitDirection();
            } else {
                if(ball.getY() > maxY){
                    ballInArea=!ballInArea;
                    setWillHit();
                    if(difficulty == Difficulty.HARD)
                        generateHitDirection();
                }
            }

        switch (difficulty) {
            case EASY:
                return getEasyDestination(ai, ball);
            case MEDIUM:
                return getMediumDestination(ai, ball);
            case HARD:
                return getHardDestination(ai, ball);
            default:
                return getMediumDestination(ai, ball);
        }
    }

    private Point getEasyDestination(Player ai, Ball ball) {
        Point point = new Point();
        if (ball.getX() > ai.getX() + EASY_SPEED)
            point.setX(ai.getX() + EASY_SPEED);
        else if (ball.getX() < ai.getX() - EASY_SPEED)
            point.setX(ai.getX() - EASY_SPEED);
        else
            point.setX(ball.getX());
        return point;
    }

    private Point getMediumDestination(Player ai, Ball ball) {
        Point point = new Point();
        if (ball.getX() > (ai.getX() + MEDIUM_SPEED))
            point.setX(ai.getX() + MEDIUM_SPEED);
        else if (ball.getX() < (ai.getX() - MEDIUM_SPEED))
            point.setX(ai.getX() - MEDIUM_SPEED);
        else
            point.setX(ball.getX());

        if ((ball.getY() > ((hitY + ai.getHeight() / 2) + MEDIUM_SPEED)) || (ball.getY() < ai.getY()))
            point.setY(ai.getY() - MEDIUM_SPEED);
        else if (isBallInRange(ball, ai, MEDIUM_SPEED))
            if(willHit)
                point.setY(ai.getY() + MEDIUM_SPEED);

        return point;
    }

    private boolean isBallInRange(Ball ball, Player ai, double speed) {
        if (ball.getX() < ai.getX() + speed || ball.getX() > ai.getX() - speed)
            if (ball.getY() < ai.getY() + speed || ball.getY() > ai.getY() - speed)
                return true;
        return false;
    }

    private Point getHardDestination(Player ai, Ball ball) {
        Point point = new Point();

        return point;
    }

    public void setWillHit()
    {
        Random random = new Random();
        willHit = random.nextBoolean();
    }

    private void generateHitDirection(){
        Random random = new Random();
        int i = random.nextInt(3);
        switch (i){
            case 0:
                hitDirection = Direction.LEFT;
                break;
            case 1:
                hitDirection = Direction.CENTER;
                break;
            case 2:
                hitDirection = Direction.RIGHT;
                break;
            default:
                hitDirection = Direction.CENTER;
        }
    }
}
