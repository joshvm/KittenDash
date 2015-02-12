package cats.kd.entity;

import android.graphics.Canvas;
import cats.kd.Game;
import cats.kd.misc.RandomPaint;
import cats.kd.utils.Utils;

public class Obstacle extends Entity{

    public static final int[] WIDTHS = {20, 70};
    public static final int[] HEIGHTS = {20, 70};
    public static final float[] SPEEDS = {1.3f, 3.1f};

    public int direction;

    private final RandomPaint paint;

    public Obstacle(final Game game, final float x, final float y, final int width, final int height, final float speed, final int direction){
        super(game, x, y, width, height, speed);
        this.direction = direction;

        paint = new RandomPaint(Utils.rand(100, 300), this);
    }

    public void draw(final Canvas canvas){
        canvas.drawRect(this, paint.paint);
    }

    public void update(){
        paint.update();
        move(direction);
        if(left + width < 0)
            game.obstacles.remove(this);
    }

    public static Obstacle create(final Game game){
        final int width = Utils.rand(WIDTHS[0], WIDTHS[1]);
        final int height = Utils.rand(HEIGHTS[0], HEIGHTS[1]);
        final int x = game.getWidth() + width;
        final int y = Utils.rand(0, game.getHeight() - height);
        final float speed = Utils.rand(SPEEDS[0], SPEEDS[1]);
        return new Obstacle(game, x, y, width, height, speed, WEST);
    }
}
