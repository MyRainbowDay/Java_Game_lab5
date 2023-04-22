package com.alex.games;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.alex.games.graphics.Sprite;
import com.alex.games.graphics.SpriteBitmap;

public class LavaTile extends Tile {
    private final Sprite sprite;
    public LavaTile(SpriteBitmap spriteBitmap, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteBitmap.getLavaSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
