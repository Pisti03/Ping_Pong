package szakdolgozat.istvan.ping_pong;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class Ball {
    private double diameter;
    private double x,y;
    private double veloX, veloY;
    private int color;

    public Ball(double diameter, double x, double y, int color) {
        this.diameter = diameter;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getDiameter() {
        return diameter;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVeloX() {
        return veloX;
    }

    public double getVeloY() {
        return veloY;
    }

    public int getColor() {
        return color;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVeloX(double veloX) {
        this.veloX = veloX;
    }

    public void setVeloY(double veloY) {
        this.veloY = veloY;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void reverseX()
    {
        this.veloX*=-1;
    }

    public void reverseY()
    {
        this.veloY*=-1;
    }

    public void nextPosition()
    {
        this.x+=this.veloX;
        this.y+=this.veloY;
    }

    public void setPosition(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    public void generateNewDirection()
    {
        Random r = new Random();
        ArrayList<Point> x = new ArrayList<>();
        x.add(new Point(0,1));
        x.add(new Point(1,1));
        x.add(new Point(0,-1));
        x.add(new Point(-1,1));
        x.add(new Point(-1,-1));
        x.add(new Point(1,-1));
        Collections.shuffle(x);
        this.veloX =  (r.nextInt(9-5) + 5) * x.get(0).getX();
        this.veloY =  (r.nextInt(9-5) + 5) * x.get(0).getY();
    }
}
