package com.alex.games;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.alex.games.graphics.Sprite;
import com.alex.games.graphics.SpriteBitmap;

public class TreeTile extends Tile {

    private final Sprite grassSprite;
    private final Sprite treeSprite;

    public TreeTile(SpriteBitmap spriteBitmap, Rect mapLocationRect) {
        super(mapLocationRect);
        grassSprite = spriteBitmap.getGrassSprite();
        treeSprite = spriteBitmap.getTreeSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        grassSprite.draw(canvas, mapLocation.left, mapLocation.top);
        treeSprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
