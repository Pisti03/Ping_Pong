package szakdolgozat.istvan.ping_pong;

import android.graphics.Color;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class Player {
    private int score;
    private double X, Y, lastX, lastY;
    public double width, height;
    private int color;
    //private Pickup pickup;

    public Player(double X, double Y, double width, double height, int color) {
        this.score = 0;
        this.X = X;
        this.Y = Y;
        this.lastX = X;
        this.lastY = Y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void increaseScore(int score)
    {
        this.score+=score;
    }

    public int getScore() {
        return score;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public int getColor() {
        return color;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }


    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setX(double X) {
        this.lastX = this.X;
        this.X = X;
    }

    public void setY(double Y) {
        this.lastY = this.Y;
        this.Y = Y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
