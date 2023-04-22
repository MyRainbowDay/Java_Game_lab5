package com.alex.games.object;

import android.graphics.Canvas;

import com.alex.games.GameDisplay;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double directionX = 1;
    protected double directionY = 0;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    protected static double getDistanceBetweenObjects(GameObject object1, GameObject object2) {
        return Math.sqrt(
                Math.pow(object2.getPositionX() - object1.getPositionX(), 2) +
                Math.pow(object2.getPositionY() - object1.getPositionY(), 2)
        );
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }
}
