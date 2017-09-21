package szakdolgozat.istvan.ping_pong;

/**
 * Created by Pisti on 2017. 09. 19..
 */

public class Point {
    private double X, Y;

    Point(){
        this.X = 0;
        this.Y = 0;
    }

    Point(double X, double Y)
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
