package szakdolgozat.istvan.ping_pong;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class GameEngine {
    private GameState gameState;
    private double screenWidth, screenHeight;
    private boolean isAI = false;
    private AI ai;
    private Sound sound;
    private Boolean endgame;
    private int goal;

    public GameEngine(double x, double y, int goal, Sound sound) {
        this.screenWidth = x;
        this.screenHeight = y;
        this.endgame = false;
        this.goal = goal;
        this.sound = sound;
        restart();
    }

    public GameEngine(double x, double y, boolean isAI, Sound sound) {
        this.screenWidth = x;
        this.screenHeight = y;
        this.isAI = isAI;
        this.sound = sound;
        this.ai = new AI(Difficulty.MEDIUM, screenWidth, screenHeight * 1 / 4);
        if (isAI)
            gameState.getPlayer2().setMoving(true);
        restart();
    }

    public GameEngine(double x, double y, Difficulty difficulty, int goal, Sound sound) {
        this.screenWidth = x;
        this.screenHeight = y;
        this.isAI = true;
        this.ai = new AI(difficulty, screenWidth, screenHeight * 1 / 4);
        this.goal = goal;
        this.endgame = false;
        this.sound = sound;
        restart();
        getGameState().getPlayer2().setMoving(true);
    }

    public void restart() {
        gameState = new GameState(screenWidth, screenHeight);
        endgame = false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean testCollision(Player player, Ball ball) {
        double ballRightSide = ball.getX() + ball.getVeloX() + ball.getDiameter() / 2;
        double ballLeftSide = ball.getX() + ball.getVeloX() - ball.getDiameter() / 2;
        double ballUpSide = ball.getY() + ball.getVeloY() - ball.getDiameter() / 2;
        double ballDownSide = ball.getY() + ball.getVeloY() + ball.getDiameter() / 2;
        double playerLeftSide = player.getX() - player.getWidth() / 2;
        double playerRightSide = player.getX() + player.getWidth() / 2;
        double playerUpSide = player.getY() - player.getHeight() / 2;
        double playerDownSide = player.getY() + player.getHeight() / 2;

        switch (getDirection(ball, player)) {
            case UP:
                if (ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballDownSide >= playerUpSide)
                    return true;
                break;
            case DOWN:
                if (ball.getX() >= playerLeftSide && ball.getX() <= playerRightSide && ballUpSide <= playerDownSide)
                    return true;
                break;
            case RIGHT:
                if (ballLeftSide <= playerRightSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide)
                    return true;
                break;
            case LEFT:
                if (ballRightSide >= playerLeftSide && ball.getY() >= playerUpSide && ball.getY() <= playerDownSide)
                    return true;
                break;
        }

        return false;
    }

    private void setSpeed(Player player) {
        double newVeloX, newVeloY, X, Y, lastX, lastY;
        Ball ball = gameState.getBall();
        X = player.getX();
        Y = player.getY();
        lastX = player.getLastX();
        lastY = player.getLastY();

        int direction;
        if (player.getY() > screenHeight / 2)
            direction = -1;
        else
            direction = 1;

        if (X >= lastY)
            newVeloX = X - lastX;
        else
            newVeloX = lastX - X;

        if (Y >= lastY)
            newVeloY = 0;
        else
            newVeloY = lastY - Y;

        if (Math.abs(newVeloX) > 0.3 && player.isMoving()) {
            ball.setVeloX(direction * newVeloX);
        }

        if (Math.abs(newVeloY) > 0.3 && player.isMoving()) {
            ball.setVeloY(direction * newVeloY);
        } else {
            ball.reverseY();
        }
    }

    private void outOfMap() {
        Ball ball = gameState.getBall();
        if (ball.getY() <= 0) {
            gameState.getPlayer1().increaseScore(1);
        } else {
            gameState.getPlayer2().increaseScore(1);
        }
        ball.setPosition(screenWidth / 2, screenHeight / 2);
        ball.generateNewDirection();
    }

    private Direction getDirection(Ball ball, Player player) {
        double x, y;
        x = ball.getX();
        y = ball.getY();
        if (y - ball.getDiameter() / 2 < player.getY() - player.getHeight() / 2 && x >= player.getX() - player.getWidth() / 2 && x <= player.getX() + player.getWidth() / 2)
            return Direction.UP;

        if (y + ball.getDiameter() / 2 > player.getY() + player.getHeight() / 2 && x >= player.getX() - player.getWidth() / 2 && x <= player.getX() + player.getWidth() / 2)
            return Direction.DOWN;

        if (x < player.getX() - player.getWidth() / 2 && y < player.getY() - player.getHeight() / 2 && y > player.getY() + player.getHeight() / 2)
            return Direction.LEFT;

        if (x > player.getX() + player.getWidth() / 2 && y < player.getY() - player.getHeight() / 2 && y > player.getY() + player.getHeight() / 2)
            return Direction.RIGHT;

        if (y - ball.getDiameter() / 2 < player.getY() - player.getHeight() / 2 && x > player.getX() + player.getWidth())
            return Direction.UPRIGHT;

        if (y + ball.getDiameter() / 2 > player.getY() + player.getHeight() / 2 && x > player.getX() + player.getWidth() / 2)
            return Direction.DOWNRIGHT;

        if (y - ball.getDiameter() / 2 < player.getY() - player.getHeight() / 2 && x < player.getX() - player.getWidth() / 2)
            return Direction.UPLEFT;

        if (y + ball.getDiameter() / 2 > player.getY() + player.getHeight() / 2 && x < player.getX() - player.getWidth() / 2)
            return Direction.DOWNLEFT;

        if (player.getY() < screenHeight / 2)
            return Direction.DOWN;
        else
            return Direction.UP;
    }

    public void moveBallOutOfPlayer(Player player, Ball ball) {
        switch (getDirection(ball, player)) {
            case UP:
                ball.setY(player.getY() - player.getHeight() / 2 - 0.5 - ball.getDiameter() / 2);
                break;
            case DOWN:
                ball.setY(player.getY() + player.getHeight() / 2 + 0.5 + ball.getDiameter() / 2);
                break;
            case RIGHT:
                ball.setX(player.getX() + player.getWidth() / 2 + 0.5 + ball.getDiameter() / 2);
                break;
            case LEFT:
                ball.setX(player.getX() - player.getWidth() / 2 - 0.5 - ball.getDiameter() / 2);
                break;
            case UPRIGHT:
                ball.setY(player.getY() - player.getHeight() / 2 - 0.5 - ball.getDiameter() / 2);
                ball.setX(player.getX() + player.getWidth() / 2 + 0.5 + ball.getDiameter() / 2);
                break;
            case DOWNRIGHT:
                ball.setY(player.getY() + player.getHeight() / 2 + 0.5 + ball.getDiameter() / 2);
                ball.setX(player.getX() + player.getWidth() / 2 + 0.5 + ball.getDiameter() / 2);
                break;
            case UPLEFT:
                ball.setX(player.getX() + player.getWidth() / 2 + 0.5 + ball.getDiameter() / 2);
                ball.setX(player.getX() - player.getWidth() / 2 - 0.5 - ball.getDiameter() / 2);
                break;
            case DOWNLEFT:
                ball.setX(player.getX() - player.getWidth() / 2 - 0.5 - ball.getDiameter() / 2);
                ball.setX(player.getX() - player.getWidth() / 2 - 0.5 - ball.getDiameter() / 2);
                break;
        }
    }

    public void collision() {
        Ball ball = gameState.getBall();
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        if (ball.getX() + ball.getDiameter() / 2 >= screenWidth) {
            ball.setX(screenWidth - (ball.getDiameter() / 2) - 0.2);
            ball.reverseX();
            sound.playWallSound();
        }

        if (ball.getX() - ball.getDiameter() / 2 <= 0) {
            ball.setX((ball.getDiameter() / 2) + 0.2);
            ball.reverseX();
            sound.playWallSound();
        }

        if (ball.getY() > screenHeight || ball.getY() < 0) {
            //sound.playWinSound();
            outOfMap();
        }

        if (testCollision(player1, ball)) {
            sound.playPlayerSound();
            moveBallOutOfPlayer(player1, ball);
            setSpeed(gameState.getPlayer1());
        }

        if (testCollision(gameState.getPlayer2(), gameState.getBall())) {
            sound.playPlayerSound();
            moveBallOutOfPlayer(player2, ball);
            setSpeed(gameState.getPlayer2());
        }

    }

    public void movePlayer1(double x, double y, Player player) {
        if (!((x - player.getWidth() / 2) < 0 || (x + player.getWidth() / 2) > screenWidth)) {
            player.setX(x);
        }

        if (!((y - player.getHeight() / 2) < screenHeight - (screenHeight * 1 / 4) || (y + player.getHeight() / 2) > screenHeight - (screenHeight * 1 / 12))) {
            player.setY(y);
        } else if (y < screenHeight - (screenHeight * 1 / 4)) {
            player.setY(screenHeight - (screenHeight * 1 / 4) + (player.getHeight() / 2));
        } else if (y > screenHeight - (screenHeight * 1 / 12)) {
            player.setY(screenHeight - (screenHeight * 1 / 12) - player.getHeight() / 2);
        }
    }

    public void movePlayer2(double x, double y, Player player) {
        if (!((x - player.getWidth() / 2) < 0 || (x + player.getWidth() / 2) > screenWidth)) {
            player.setX(x);
        }

        if (!((y + player.getHeight() / 2) > screenHeight * 1 / 4 || (y - player.getHeight() / 2) < screenHeight * 1 / 12)) {
            player.setY(y);
        } else if (y < screenHeight * 1 / 12 && !isAI) {
            player.setY(screenHeight * 1 / 12 + (player.getHeight() / 2));
        } else if (y > screenHeight * 1 / 4 && !isAI) {
            player.setY(screenHeight * 1 / 4 - player.getHeight() / 2);
        }
    }

    public void setPlayersLastPosition() {
        gameState.getPlayer1().setLast();
        gameState.getPlayer2().setLast();
    }

    public boolean pointInPlayer(Player player, double x, double y) {
        double playerLeftSide = player.getX() - player.getWidth() / 2;
        double playerRightSide = player.getX() + player.getWidth() / 2;
        double playerUpSide = player.getY() - player.getHeight() / 2;
        double playerDownSide = player.getY() + player.getHeight() / 2;

        if (x > playerLeftSide && x < playerRightSide)
            if (y > playerUpSide && y < playerDownSide)
                return true;

        return false;
    }

    public void nextStep() {
        Player player1, player2;
        player1 = getGameState().getPlayer1();
        player2 = getGameState().getPlayer2();
        Ball ball = gameState.getBall();
        ball.nextPosition();
        collision();
        setPlayersLastPosition();
        if (isAI) {
            Point dest = ai.getDestination(gameState);
            movePlayer2(dest.getX(), dest.getY(), gameState.getPlayer2());
        }

        if (player1.getScore() >= goal || player2.getScore() >= goal) {
            endgame = true;
            ball.setX(10000);
        }

    }

    public boolean isGameEnded() {
        return endgame;
    }
}
