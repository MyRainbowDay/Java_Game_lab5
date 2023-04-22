package com.alex.games;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.alex.games.graphics.Sprite;
import com.alex.games.graphics.SpriteBitmap;

public class GrassTile extends Tile {

    private final Sprite sprite;
    public GrassTile(SpriteBitmap spriteBitmap, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteBitmap.getGrassSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
