package cats.kd.entity;

import android.graphics.Canvas;
import android.graphics.RectF;
import cats.kd.Game;
import cats.kd.misc.RandomPaint;
import cats.kd.utils.Utils;

public class Projectile extends Entity{

    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;
    public static final float SPEED = 2.7f;

    private final RandomPaint paint;

    public Projectile(final Game game, final float x, final float y){
        super(game, x, y, WIDTH, HEIGHT, SPEED);

        paint = new RandomPaint(Utils.rand(100, 300), this);
    }

    public void update(){
        paint.update();
        move(EAST);
        if(top > game.getWidth()){
            game.kitten.projectiles.remove(this);
            return;
        }
        for(final Obstacle o : game.obstacles){
            if(RectF.intersects(this, o)){
                game.obstacles.remove(o);
                game.kitten.projectiles.remove(this);
                break;
            }
        }
    }

    public void draw(final Canvas canvas){
        canvas.drawRoundRect(this, 5, 5, paint.paint);
    }
}
