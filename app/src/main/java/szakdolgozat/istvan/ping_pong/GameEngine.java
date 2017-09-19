package szakdolgozat.istvan.ping_pong;

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

        if(ballRightSide >= playerLeftSide && ballRightSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide
           || ballLeftSide >= playerLeftSide && ballLeftSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide
           || ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballUpSide >= playerDownSide && ballUpSide <= playerDownSide
           || ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballDownSide >= playerUpSide && ballDownSide <= playerDownSide)
            return true;

        return false;
    }

    private void setSpeed(Player player)
    {
        double newVeloX, newVeloY, posX, posY, lastPosX, lastPosY;
        posX = player.getPosX();
        posY = player.getPosY();
        lastPosX = player.getLastPosX();
        lastPosY = player.getLastPosY();

        if(posX >= lastPosY)
            newVeloX = posX-lastPosX;
        else
            newVeloX = lastPosX-posX;

        if(posY >= lastPosY)
            newVeloY = 0;
        else
            newVeloY = lastPosY - posY;

        if(newVeloX != 0 && newVeloY != 0) {
            gameState.getBall().setVeloX(newVeloX);
            gameState.getBall().setVeloY(-newVeloY - 1);
        } else {
            gameState.getBall().reverseY();
        }
    }

    private void outOfMap()
    {
        Ball ball = gameState.getBall();
        if(ball.getY() <= 0)
        {
            //player1 score;
        } else {
            //player2 score
        }
        ball.setPosition(screenWidth/2, screenHeight/2);
        ball.generateNewDirection();
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

        if(nextY - radius > screenHeight || nextY + radius < 0)
        {
            outOfMap();
        }

        if(testCollision(gameState.getPlayer1(), gameState.getBall())) {
            setSpeed(gameState.getPlayer1());

        }

        if(testCollision(gameState.getPlayer2(), gameState.getBall())){
            setSpeed(gameState.getPlayer2());
        }

    }

    public void movePlayer(double x, double y, Player player) {
        if(!((x - player.getWidth()/2) < 0 || (x + player.getWidth()/2) > screenWidth))
        {
            player.setPosX(x);
        }

        if(!((y - player.getHeight()/2) < 0 || (y + player.getHeight()/2) > screenHeight)){
            player.setPosY(y);
        }
    }

    public void nextStep(){
        Ball ball = gameState.getBall();
        collision();
        ball.nextPosition();
        movePlayer(ball.getX(), ball.getY(), gameState.getPlayer2());
    }
}
