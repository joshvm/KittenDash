package cats.kd.entity;

import android.graphics.Canvas;
import android.graphics.RectF;
import cats.kd.Game;

public abstract class Entity extends RectF{

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    public final Game game;
    public float speed;
    public int width;
    public int height;

    Entity(final Game game, final float x, final float y, final int width, final int height, final float speed){
        this.game = game;
        this.width = width;
        this.height = height;
        this.speed = speed;

        set(x, y);
    }

    Entity(final Game game, final float x, final float y, final float speed){
        this(game, x, y, 0, 0, speed);
    }

    public void set(final float x, final float y){
        left = x;
        top = y;
        right = left + width;
        bottom = top + height;
    }

    public void move(final int direction){
        switch(direction){
            case NORTH:
                set(left, top - speed);
                break;
            case EAST:
                set(left + speed, top);
                break;
            case SOUTH:
                set(left, top + speed);
                break;
            case WEST:
                set(left - speed, top);
                break;
        }
    }

    public abstract void update();

    public abstract void draw(final Canvas canvas);
}
