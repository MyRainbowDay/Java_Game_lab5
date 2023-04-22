package com.alex.games;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.alex.games.graphics.Sprite;
import com.alex.games.graphics.SpriteBitmap;

public class WaterTile extends Tile {
    private final Sprite sprite;
    public WaterTile(SpriteBitmap spriteBitmap, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteBitmap.getWaterSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
