package com.alex.games;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.alex.games.gamepanel.GameOver;
import com.alex.games.gamepanel.Joystick;
import com.alex.games.gamepanel.Performance;
import com.alex.games.graphics.Animator;
import com.alex.games.graphics.SpriteBitmap;
import com.alex.games.object.Circle;
import com.alex.games.object.Enemy;
import com.alex.games.object.Player;
import com.alex.games.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Joystick joystick;
    private final Tilemap tileMap;
    //private final Enemy enemy;
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Spell> spellList = new ArrayList<>();
    private Gameloop gameLoop;
    private final Player player;
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);

        // Get surface holder and callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new Gameloop(this, surfaceHolder);

        // game panel
        gameOver = new GameOver(context);
        performance = new Performance(context, gameLoop);
        joystick = new Joystick(275, 900, 120, 90);

        // Game objects
        SpriteBitmap spriteBitmap = new SpriteBitmap(context);
        Animator animator = new Animator(spriteBitmap.getPlayerSpriteArray());
        player = new Player(context, joystick,500, 500, 32, animator);

        // initialize game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        
        tileMap = new Tilemap(spriteBitmap);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Handle touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    // joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast++;
                }
                else if (joystick.isPressed((double)event.getX(), (double)event.getY())) {
                    // joystick is pressed in this event
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // joystick was not previously, and not pressed in this event -> cast spell
                    numberOfSpellsToCast++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // joystick was pressed previously and is now moved
                if (joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick was let go of
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated");

        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new Gameloop(this, holder);
        }

        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        tileMap.draw(canvas, gameDisplay);

        player.draw(canvas, gameDisplay);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }

        // game panels
        joystick.draw(canvas);
        performance.draw(canvas);

        // draw game over
        if (player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {

        // stop updating the game if the player is dead
        if (player.getHealthPoints() <= 0) {
            return;
        }


        joystick.update();
        player.update();

        // Spawn Enemy
        if (Enemy.ReadyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Update Enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        // Update Spell
        while (numberOfSpellsToCast > 0 ) {
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast--;
        }
        for (Spell spell : spellList) {
            spell.update();
        }

        // Collision between enemy and player and spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // Remove enemy
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }

}
