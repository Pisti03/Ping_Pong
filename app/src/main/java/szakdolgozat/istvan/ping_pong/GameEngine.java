package szakdolgozat.istvan.ping_pong;

import szakdolgozat.istvan.ping_pong.GameState;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameEngine {
    private GameState gameState;
    private double screenWidth, screenHeight;

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

        /*if((nextX >= gameState.getPlayer1().getPosX()-gameState.getPlayer1().getWidth()/2 && nextX <= gameState.getPlayer1().getPosX()+gameState.getPlayer1().getWidth()/2)
                && (nextY + radius >= gameState.getPlayer1().getPosY()-gameState.getPlayer1().getHeight()/2 && nextY - radius <= gameState.getPlayer1().getPosY()+gameState.getPlayer1().getHeight()/2))
        {
            gameState.getBall().reverseY();
            if(nextX < gameState.getPlayer1().getPosX())
                gameState.getBall().setVeloX(Math.abs(gameState.getBall().getVeloX())*-1);
            else
                gameState.getBall().setVeloX(Math.abs(gameState.getBall().getVeloX()));
        }

        if((nextY >= gameState.getPlayer1().getPosY()-gameState.getPlayer1().getHeight()/2 && nextY <= gameState.getPlayer1().getPosY()+gameState.getPlayer1().getHeight()/2)
                && (nextX + radius >= gameState.getPlayer1().getPosX()-gameState.getPlayer1().getWidth()/2 && nextX - radius <= gameState.getPlayer1().getPosX()+gameState.getPlayer1().getWidth()/2)) {
            gameState.getBall().reverseX();
            gameState.getBall().reverseY();
        }

        if((nextX >= gameState.getPlayer2().getPosX()-gameState.getPlayer2().getWidth()/2 && nextX <= gameState.getPlayer2().getPosX()+gameState.getPlayer2().getWidth()/2)
                && (nextY - radius <= gameState.getPlayer2().getPosY()+gameState.getPlayer2().getHeight()/2 && nextY + radius >= gameState.getPlayer2().getPosY()-gameState.getPlayer2().getHeight()/2))
            gameState.getBall().reverseY();

        if((nextY <= gameState.getPlayer2().getPosY()+gameState.getPlayer2().getHeight()/2 && nextY >= gameState.getPlayer2().getPosY()-gameState.getPlayer2().getHeight()/2)
                && (nextX + radius >= gameState.getPlayer2().getPosX()-gameState.getPlayer2().getWidth()/2 && nextX - radius <= gameState.getPlayer2().getPosX()+gameState.getPlayer2().getWidth()/2)) {
            gameState.getBall().reverseX();
            gameState.getBall().reverseY();
        }*/
        if(testCollision(gameState.getPlayer1(), gameState.getBall())) {
            double newVeloX, newVeloy, posX, posY, lastPosX, lastPosY;
            posX = gameState.getPlayer1().getPosX();
            posY = gameState.getPlayer1().getPosY();
            lastPosX = gameState.getPlayer1().getLastPosX();
            lastPosY = gameState.getPlayer1().getLastPosY();

            /*System.out.println("posX: " + posX );
            System.out.println("posY: " + posY );
            System.out.println("lastPosX: " + lastPosX );
            System.out.println("lastPosY: " + lastPosY );*/

            if(posX >= lastPosY)
                newVeloX = posX-lastPosX;
            else
                newVeloX = lastPosX-posX;

            if(posY >= lastPosY)
                newVeloy = 0;
            else
                newVeloy = lastPosY - posY;

            /*System.out.println("newVeloX: " + newVeloX);
            System.out.println("newVeloy: " + newVeloy);
            System.out.println("actprevVeloX: " + getGameState().getBall().getVeloX());
            System.out.println("actprevVeloY: " + getGameState().getBall().getVeloY());*/

            gameState.getBall().setVeloX(gameState.getBall().getVeloX()+newVeloX);
            gameState.getBall().setVeloY(gameState.getBall().getVeloY()-newVeloy);
            /*System.out.println("actnewVeloX: " + getGameState().getBall().getVeloX());
            System.out.println("actnewVeloY: " + getGameState().getBall().getVeloY());*/
            // gameState.getBall().reverseY();
        }

        if(testCollision(gameState.getPlayer2(), gameState.getBall())){
            double newVeloX, newVeloy, posX, posY, lastPosX, lastPosY;
            posX = gameState.getPlayer2().getPosX();
            posY = gameState.getPlayer2().getPosY();
            lastPosX = gameState.getPlayer2().getLastPosX();
            lastPosY = gameState.getPlayer2().getLastPosY();

            if(posX >= lastPosY)
                newVeloX = posX-lastPosX;
            else
                newVeloX = lastPosX-posX;

            if(posY >= lastPosY)
                newVeloy = 0;
            else
                newVeloy = lastPosY - posY;

            gameState.getBall().setVeloX(gameState.getBall().getVeloX()+newVeloX);
            gameState.getBall().setVeloY(10);
        }

    }

    public void movePlayer1(double x, double y) {
        if(!(((x-gameState.getPlayer1().getWidth()/2) < 0 || (x + gameState.getPlayer1().getWidth()/2) > screenWidth) && (y-gameState.getPlayer1().getHeight()/2) < 0 || (y + gameState.getPlayer1().getHeight()/2) > screenHeight))
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
