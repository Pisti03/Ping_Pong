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

        if((ballRightSide >= playerLeftSide && ballRightSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide)
           || ballLeftSide >= playerLeftSide && ballLeftSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide
           || ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballUpSide >= playerDownSide && ballUpSide <= playerDownSide
           || ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballDownSide >= playerUpSide && ballDownSide <= playerDownSide)
            return true;

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

        if(posX >= lastPosY)
            newVeloX = posX-lastPosX;
        else
            newVeloX = lastPosX-posX;

        if(posY >= lastPosY)
            newVeloY = 0;
        else
            newVeloY = lastPosY - posY;

        if(newVeloX != 0) {
            gameState.getBall().setVeloX(-newVeloX);
        } else {
            gameState.getBall().reverseX();
        }

        if(newVeloY != 0){
            gameState.getBall().setVeloY(-newVeloY);
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
        Point A, B, C, D, E, F;

        A = new Point(ball.getX()-(Math.signum(ball.getVeloX()*(ball.getDiameter()/2))), ball.getY()-(Math.signum(ball.getVeloY()*(ball.getDiameter()/2))));
        B = new Point(ball.getX()+ball.getVeloX(), ball.getY()+ball.getVeloY());
        C = new Point(player1.getPosX()-player1.getWidth()/2, player1.getPosY()-player1.getHeight()/2);
        D = new Point(player1.getPosX()+player1.getWidth()/2, player1.getPosY()-player1.getHeight()/2);
        E = new Point(player1.getPosX()-player1.getWidth()/2, player1.getPosY()+player1.getHeight()/2);
        F = new Point(player1.getPosX()+player1.getWidth()/2, player1.getPosY()+player1.getHeight()/2);

        if((ball.getX() >= screenWidth) || (ball.getX() <= 0))
        {
            gameState.getBall().reverseX();
        }

        if(ball.getY() > screenHeight || ball.getY() < 0)
        {
            outOfMap();
        }



        if(doIntersect(A,B,C,D) || doIntersect(A,B,E,F) || testCollision(gameState.getPlayer1(), gameState.getBall()))
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

        C = new Point(player2.getPosX()-player2.getWidth()/2, player2.getPosY()-player2.getHeight()/2);
        D = new Point(player2.getPosX()+player2.getWidth()/2, player2.getPosY()-player2.getHeight()/2);
        E = new Point(player2.getPosX()-player2.getWidth()/2, player2.getPosY()+player2.getHeight()/2);
        F = new Point(player2.getPosX()+player2.getWidth()/2, player2.getPosY()+player2.getHeight()/2);

        if(doIntersect(A,B,C,D) || doIntersect(A,B,E,F) || testCollision(gameState.getPlayer2(), gameState.getBall())){
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
        if(isAI) {
            Point dest = ai.getDestination(gameState);
            movePlayer(dest.getX(), dest.getY(), gameState.getPlayer2());
        }
    }

    boolean onSegment(Point p, Point q, Point r)
    {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
            return true;

        return false;
    }


    int orientation(Point a, Point b, Point c)
    {
        int orientation = (int)((b.getY() - a.getY()) * (c.getX() - b.getX()) -
                (b.getX() - a.getX()) * (c.getY() - b.getY()));

        if (orientation == 0)
            return 0;

        return (orientation > 0) ? 1 : 2;
    }


    boolean doIntersect(Point a, Point b, Point c, Point d)
    {
        int o1 = orientation(a, b, c);
        int o2 = orientation(a, b, d);
        int o3 = orientation(c, d, a);
        int o4 = orientation(c, d, b);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(a, c, b))
            return true;

        if (o2 == 0 && onSegment(a, d, b))
            return true;

        if (o3 == 0 && onSegment(c, a, d))
            return true;

        if (o4 == 0 && onSegment(c, b, d))
            return true;

        return false;
    }
}
