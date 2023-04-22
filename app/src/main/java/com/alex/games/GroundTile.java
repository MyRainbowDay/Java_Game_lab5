package com.alex.games;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.alex.games.graphics.Sprite;
import com.alex.games.graphics.SpriteBitmap;

public class GroundTile extends Tile {
    private final Sprite sprite;

    public GroundTile(SpriteBitmap spriteBitmap, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteBitmap.getGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
