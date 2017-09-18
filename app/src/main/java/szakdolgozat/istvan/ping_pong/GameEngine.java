package szakdolgozat.istvan.ping_pong;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameEngine {
    private GameState gameState;
    private double screenWidth, screenHeight;
    private static double MAX_MOVE_AREA;//TODO

    public GameEngine(double x, double y) {
        this.screenWidth = x;
        this.screenHeight = y;
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

        if(ballRightSide >= playerLeftSide && ballRightSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide )
            return true;
        if(ballLeftSide >= playerLeftSide && ballLeftSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide )
            return true;
        if(ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballUpSide >= playerDownSide && ballUpSide <= playerDownSide )
            return true;
        if(ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballDownSide >= playerUpSide && ballDownSide <= playerDownSide )
            return true;

        return false;
    }

    public void collision(){
        double nextX, nextY;
        nextX = gameState.getBall().getX() + gameState.getBall().getVeloX();
        nextY = gameState.getBall().getY() + gameState.getBall().getVeloY();
        double radius = gameState.getBall().getDiameter()/2;
        if((nextX + radius >= screenWidth) || (nextX - radius <= 0))
        {
            gameState.getBall().reverseX();
        }

        if(nextY >= screenHeight)
        {
            gameState.getBall().setPosition(screenWidth/2, screenHeight/2);
            gameState.getBall().generateNewDirection();
        }

        if(nextY <= 0)
        {
            gameState.getBall().setPosition(screenWidth/2, screenHeight/2);
            gameState.getBall().generateNewDirection();
        }

        if(testCollision(gameState.getPlayer1(), gameState.getBall())) {
            double newVeloX, newVeloY, posX, posY, lastPosX, lastPosY;
            posX = gameState.getPlayer1().getPosX();
            posY = gameState.getPlayer1().getPosY();
            lastPosX = gameState.getPlayer1().getLastPosX();
            lastPosY = gameState.getPlayer1().getLastPosY();

            if(posX >= lastPosY)
                newVeloX = posX-lastPosX;
            else
                newVeloX = lastPosX-posX;

            if(posY >= lastPosY)
                newVeloY = 0;
            else
                newVeloY = lastPosY - posY;

            if(newVeloX == 0 && newVeloY == 0) {
                gameState.getBall().reverseY();
            } else {
                gameState.getBall().setVeloX(gameState.getBall().getVeloX() - newVeloX);
                gameState.getBall().setVeloY(gameState.getBall().getVeloY() - newVeloY);
            }

        }

        if(testCollision(gameState.getPlayer2(), gameState.getBall())){
            double newVeloX, newVeloY, posX, posY, lastPosX, lastPosY;
            posX = gameState.getPlayer2().getPosX();
            posY = gameState.getPlayer2().getPosY();
            lastPosX = gameState.getPlayer2().getLastPosX();
            lastPosY = gameState.getPlayer2().getLastPosY();

            if(posX >= lastPosY)
                newVeloX = posX-lastPosX;
            else
                newVeloX = lastPosX-posX;

            if(posY >= lastPosY)
                newVeloY = 0;
            else
                newVeloY = lastPosY - posY;

            if(newVeloX == 0 && newVeloY == 0) {
                gameState.getBall().reverseY();
            } else {
                gameState.getBall().setVeloX(gameState.getBall().getVeloX() + newVeloX);
                gameState.getBall().setVeloY(10);
            }
        }

    }

    public void movePlayer1(double x, double y) {
        if(!(((x-gameState.getPlayer1().getWidth()/2) < 0 || (x + gameState.getPlayer1().getWidth()/2) > screenWidth)
                || (y-gameState.getPlayer1().getHeight()/2) < 0 || (y + gameState.getPlayer1().getHeight()/2) > screenHeight))
        {
            if(gameState.getPlayer1().getMaxSpeed()!=0) {
                if (Math.abs(gameState.getPlayer1().getPosX() - gameState.getBall().getX()) < gameState.getPlayer1().getMaxSpeed())
                    gameState.getPlayer1().setPosX(gameState.getBall().getX());
                else if (x < gameState.getPlayer1().getPosX())
                    gameState.getPlayer1().setPosX(gameState.getPlayer1().getPosX() - gameState.getPlayer1().getMaxSpeed());
                else
                    gameState.getPlayer1().setPosX(gameState.getPlayer1().getPosX() + gameState.getPlayer1().getMaxSpeed());
            } else
                gameState.getPlayer1().setPosX(x);
                gameState.getPlayer1().setPosY(y);
        }
    }

    public void movePlayer2()
    {
        if(!(((gameState.getPlayer2().getPosX()-gameState.getPlayer2().getWidth()/2 < 0) && gameState.getPlayer2().getPosX()>gameState.getBall().getX())
                || (((gameState.getPlayer2().getPosX() + gameState.getPlayer2().getWidth()/2) > screenWidth) && gameState.getPlayer2().getPosX()<gameState.getBall().getX()) ))
        {
            if(gameState.getPlayer2().getMaxSpeed()!=0) {
                if (Math.abs(gameState.getPlayer2().getPosX() - gameState.getBall().getX()) < gameState.getPlayer2().getMaxSpeed())
                    gameState.getPlayer2().setPosX(gameState.getBall().getX());
                else if (gameState.getBall().getX() < gameState.getPlayer2().getPosX())
                    gameState.getPlayer2().setPosX(gameState.getPlayer2().getPosX() - gameState.getPlayer2().getMaxSpeed());
                else
                    gameState.getPlayer2().setPosX(gameState.getPlayer2().getPosX() + gameState.getPlayer2().getMaxSpeed());
            } else
                gameState.getPlayer2().setPosX(gameState.getBall().getX());
        }


    }
    public void nextStep(){
        collision();
        gameState.getBall().nextPosition();
        movePlayer2();
    }
}
