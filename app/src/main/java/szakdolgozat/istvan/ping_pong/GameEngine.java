package szakdolgozat.istvan.ping_pong;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameEngine {
    private GameState gameState;
    private double screenWidth, screenHeight;
    private boolean isAI = false;
    private AI ai;

    public GameEngine(double x, double y) {
        this.screenWidth = x;
        this.screenHeight = y;
        restart();
    }

    public GameEngine(double x, double y, boolean isAI) {
        this.screenWidth = x;
        this.screenHeight = y;
        this.isAI = isAI;
        this.ai = new AI(Difficulty.MEDIUM);
        restart();
    }

    public GameEngine(double x, double y, Difficulty difficulty ) {
        this.screenWidth = x;
        this.screenHeight = y;
        this.isAI = true;
        this.ai = new AI(difficulty);
        restart();
    }

    public void restart()
    {
        gameState = new GameState(screenWidth, screenHeight);
    }
    
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean testCollision(Player player, Ball ball)
    {
        double ballRightSide = ball.getX() + ball.getVeloX() + ball.getDiameter()/2;
        double ballLeftSide = ball.getX() + ball.getVeloX() - ball.getDiameter()/2;
        double ballUpSide = ball.getY() + ball.getVeloY() - ball.getDiameter()/2;
        double ballDownSide = ball.getY() + ball.getVeloY() + ball.getDiameter()/2;
        double playerLeftSide = player.getPosX() - player.getWidth()/2;
        double playerRightSide = player.getPosX() + player.getWidth()/2;
        double playerUpSide = player.getPosY() - player.getHeight()/2;
        double playerDownSide = player.getPosY() + player.getHeight()/2;

        switch(getDirection(ball, player)){
            case UP:
                if(ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballDownSide >= playerUpSide)
                    return true;
                break;
            case DOWN:
                if(ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballUpSide <= playerDownSide)
                    return true;
                break;
            case RIGHT:
                if(ballLeftSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide)
                    return true;
                break;
            case LEFT:
                if(ballRightSide >= playerLeftSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide)
                    return true;
                break;
        }

        return false;
    }

    private void setSpeed(Player player)
    {
        double newVeloX, newVeloY, posX, posY, lastPosX, lastPosY;
        Ball ball = gameState.getBall();
        posX = player.getPosX();
        posY = player.getPosY();
        lastPosX = player.getLastPosX();
        lastPosY = player.getLastPosY();

        int direction;
        if(player.getPosY() > screenHeight/2)
            direction = -1;
        else
            direction = 1;

        if(posX >= lastPosY)
            newVeloX = posX-lastPosX;
        else
            newVeloX = lastPosX-posX;

        if(posY >= lastPosY)
            newVeloY = 0;
        else
            newVeloY = lastPosY - posY;

        if(newVeloX != 0) {
            gameState.getBall().setVeloX(direction * newVeloX);
        } else {
            gameState.getBall().reverseX();
        }

        if(newVeloY != 0){
            gameState.getBall().setVeloY(direction * newVeloY);
        } else {
            gameState.getBall().reverseY();
        }
    }

    private void outOfMap()
    {
        Ball ball = gameState.getBall();
        if(ball.getY() <= 0)
        {
            gameState.getPlayer1().increaseScore(1);
        } else {
            gameState.getPlayer2().increaseScore(1);
        }
        ball.setPosition(screenWidth/2, screenHeight/2);
        ball.generateNewDirection();
    }

    private Direction getDirection(Ball ball, Player player)
    {
        double x,y;
        x = ball.getX();
        y = ball.getY();
        if(y < player.getPosY() && x >= player.getPosX() - player.getWidth()/2  && x <= player.getPosX() + player.getWidth()/2)
            return Direction.UP;

        if(y > player.getPosY() && x >= player.getPosX() - player.getWidth()/2  && x <= player.getPosX() + player.getWidth()/2)
            return Direction.DOWN;

        if(x < player.getPosX())
            return Direction.LEFT;

        if(x > player.getPosX())
            return  Direction.RIGHT;

        return Direction.UP;
    }


    public void collision(){
        Ball ball = gameState.getBall();
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        if(ball.getX() >= screenWidth)
        {
            ball.setX(screenWidth-(ball.getDiameter()/2)-0.2);
            ball.reverseX();
        }

        if(ball.getX() <= 0)
        {
            ball.setX((ball.getDiameter()/2)+0.2);
            gameState.getBall().reverseX();
        }

        if(ball.getY() > screenHeight || ball.getY() < 0)
        {
            outOfMap();
        }

        if(testCollision(gameState.getPlayer1(), gameState.getBall()))
        {
            switch(getDirection(ball, player1)){
                case UP:
                    ball.setY(player1.getPosY()-player1.getHeight()/2 - 0.1 - ball.getDiameter()/2);
                    break;
                case DOWN:
                    ball.setY(player1.getPosY()+player1.getHeight()/2 + 0.1 + ball.getDiameter()/2);
                    break;
                case RIGHT:
                    ball.setX(player1.getPosX()+player1.getWidth()/2 + 0.1 +  ball.getDiameter()/2);
                    break;
                case LEFT:
                    ball.setX(player1.getPosX()-player1.getWidth()/2 - 0.1 - ball.getDiameter()/2);
                    break;
            }
             setSpeed(gameState.getPlayer1());
        }

        if(testCollision(gameState.getPlayer2(), gameState.getBall())){
            switch(getDirection(ball, player2)){
                case UP:
                    ball.setY(player2.getPosY()-player2.getHeight()/2 - 0.1 - ball.getDiameter()/2);
                    break;
                case DOWN:
                    ball.setY(player2.getPosY()+player2.getHeight()/2 + 0.1 + ball.getDiameter()/2);
                    break;
                case RIGHT:
                    ball.setX(player2.getPosX()+player2.getWidth()/2 + 0.1 +  ball.getDiameter()/2);
                    break;
                case LEFT:
                    ball.setX(player2.getPosX()-player2.getWidth()/2 - 0.1 - ball.getDiameter()/2);
                    break;
            }
            setSpeed(gameState.getPlayer2());
        }

    }

    public void movePlayer1(double x, double y, Player player) {
        if(!((x - player.getWidth()/2) < 0 || (x + player.getWidth()/2) > screenWidth))
        {
            player.setPosX(x);
        }

        if(!((y - player.getHeight()/2) < screenHeight-(screenHeight*1/4) || (y + player.getHeight()/2) > screenHeight-(screenHeight*1/12))){
            player.setPosY(y);
        }
    }

    public void movePlayer2(double x, double y, Player player) {
        if(!((x - player.getWidth()/2) < 0 || (x + player.getWidth()/2) > screenWidth))
        {
            player.setPosX(x);
        }

        if(!((y + player.getHeight()/2) > screenHeight*1/4 || (y - player.getHeight()/2) < screenHeight*1/12)){
            player.setPosY(y);
        }
    }

    public void nextStep(){
        Ball ball = gameState.getBall();
        collision();
        ball.nextPosition();
        if(isAI) {
            Point dest = ai.getDestination(gameState);
            movePlayer2(dest.getX(), dest.getY(), gameState.getPlayer2());
        }
    }
}
