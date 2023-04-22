package com.alex.games.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.alex.games.GameDisplay;
import com.alex.games.Gameloop;
import com.alex.games.Utils;
import com.alex.games.gamepanel.HealthBar;
import com.alex.games.gamepanel.Joystick;
import com.alex.games.R;
import com.alex.games.graphics.Animator;
import com.alex.games.graphics.Sprite;

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / Gameloop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 10;
    private final Joystick joystick;
    private final HealthBar healthBar;
    private int healthPoints;
    private Animator animator;

    private PlayerState playerState;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, Animator animator) {
        super(
                ContextCompat.getColor(context, R.color.player),
                positionX,
                positionY,
                radius
        );
        this.joystick = joystick;
        this.healthPoints = MAX_HEALTH_POINTS;
        this.healthBar = new HealthBar(context, this);
        this.animator = animator;
        this.playerState = new PlayerState(this);
    }

   @Override
    public void update() {
        // update velocity
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;

        // update direction
       if (velocityX != 0 || velocityY != 0) {
           double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
           directionX = velocityX / distance;
           directionY = velocityY / distance;
       }

       playerState.update();
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);

        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints >= 0)
            this.healthPoints = healthPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
