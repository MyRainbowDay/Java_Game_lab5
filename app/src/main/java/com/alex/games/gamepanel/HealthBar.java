package com.alex.games.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.alex.games.GameDisplay;
import com.alex.games.R;
import com.alex.games.object.Player;

public class HealthBar {
    private Player player;
    private int width, height, margin;
    private Paint borderPaint;
    private Paint healthPaint;

    public HealthBar(Context context, Player player) {
        this.player = player;
        width = 100;
        height = 20;
        margin = 2;
        borderPaint = new Paint();
        healthPaint = new Paint();
        borderPaint.setColor(ContextCompat.getColor(context, R.color.healthBarBorder));
        healthPaint.setColor(ContextCompat.getColor(context, R.color.healthBarHealth));    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceToPlayer = 30;
        float healthPointPercentage = (float) player.getHealthPoints() / player.MAX_HEALTH_POINTS;

        // draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x - width / 2;
        borderRight = x + width / 2;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(borderLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(borderTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(borderRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(borderBottom),
                borderPaint
        );


        // draw health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2 * margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth * healthPointPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(healthLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(healthRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthBottom),
                healthPaint
        );
    }
}
