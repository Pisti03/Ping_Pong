package szakdolgozat.istvan.ping_pong;

/**
 * Created by Pisti on 2017. 09. 19..
 */

public class Coordinate {
    private double X, Y;

    Coordinate(){
        this.X = 0;
        this.Y = 0;
    }

    Coordinate(double X, double Y)
    {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }
}
