package szakdolgozat.istvan.ping_pong;

import android.graphics.Color;

/**
 * Created by Pisti on 2017. 09. 06..
 */

public class Player {
    private int score;
    private double posX, posY, lastPosX, lastPosY;
    public double width, height;
    private int color;
    double maxSpeed;
    //private Pickup pickup;

    public Player(double posX, double posY, double width, double height, int color) {
        this.score = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    public Player(double posX, double posY, double width, double height, double maxSpeed, int color) {
        this.score = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;
        this.color = color;
    }

    public void increaseScore(int score)
    {
        this.score+=score;
    }

    public int getScore() {
        return score;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
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

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getLastPosX() {
        return lastPosX;
    }

    public double getLastPosY() {
        return lastPosY;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPosX(double posX) {
        this.lastPosX = this.posX;
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.lastPosY = this.posY;
        this.posY = posY;
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

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
