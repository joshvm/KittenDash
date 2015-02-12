package cats.kd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import cats.kd.entity.Kitten;
import cats.kd.entity.Obstacle;
import cats.kd.misc.RandomPaint;
import cats.kd.utils.Utils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback{

    private static class Background extends RectF{

        private final RandomPaint paint;

        public Background(){
            paint = new RandomPaint(200, this);
        }

        public void update(){
            paint.update();
        }

        public void draw(final Canvas canvas){
            canvas.drawRect(this, paint.paint);
        }
    }

    public static final int MIN_OBSTACLE_DELAY = 500;
    public static final int MAX_OBSTACLE_DELAY = 1000;

    public final MainThread thread;

    private final Background background;

    public final Kitten kitten;

    private long lastObstacleCall;
    private int obstacleDelay;

    public final List<Obstacle> obstacles;

    public boolean paused;
    public boolean gameOver;
    private final Paint paint;

    public int score;

    public Game(final Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        paused = true;

        paint = new Paint();
        paint.setTextSize(40f);
        paint.setColor(Color.WHITE);

        obstacles = new CopyOnWriteArrayList<>();

        lastObstacleCall = System.currentTimeMillis();
        obstacleDelay = Utils.rand(MIN_OBSTACLE_DELAY, MAX_OBSTACLE_DELAY);

        background = new Background();

        kitten = new Kitten(this, 0, 0);

        thread = new MainThread(getHolder(), this);
    }

    public void surfaceCreated(final SurfaceHolder holder){
        background.right = getWidth();
        background.bottom = getHeight();
        kitten.set(15, (getHeight() / 2) - (kitten.height / 2));
        thread.start();
    }

    public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height){

    }

    public void surfaceDestroyed(final SurfaceHolder holder){
        boolean retry;
        do{
            try{
                thread.join();
                retry = true;
            }catch(Exception ex){
                retry = false;
            }
        }while(retry);
    }

    public boolean onTouchEvent(final MotionEvent e){
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(e.getX() < 50){
                    if(gameOver){
                        obstacles.clear();
                        kitten.set(15, (getHeight() / 2) - (kitten.height / 2));
                        kitten.projectiles.clear();
                        obstacleDelay = Utils.rand(MIN_OBSTACLE_DELAY, MAX_OBSTACLE_DELAY);
                        gameOver = false;
                        paused = false;
                        score = 0;
                        break;
                    }
                    paused = !paused;
                    break;
                }
                kitten.up = true;
                break;
            case MotionEvent.ACTION_UP:
                kitten.up = false;
                break;
        }
        return true;
    }

    public void update(){
        if(paused || gameOver)
            return;
        kitten.update();
        if(kitten.left < 0 || kitten.top < 0 || kitten.top + kitten.height > getHeight()){
            gameOver = true;
            return;
        }
        if(System.currentTimeMillis() - lastObstacleCall >= obstacleDelay){
            obstacles.add(Obstacle.create(this));
            lastObstacleCall = System.currentTimeMillis();
        }
        for(final Obstacle obstacle : obstacles){
            obstacle.update();
            if(RectF.intersects(kitten, obstacle)){
                gameOver = true;
                break;
            }
        }
        score++;
        if(score > 1000){
            final int extra = (score / 1000) * 100;
            obstacleDelay = Utils.rand(MIN_OBSTACLE_DELAY - extra, MAX_OBSTACLE_DELAY - extra);
        }
        if(score >= 500 && score % 500 == 0)
            background.update();
    }

    public void onDraw(final Canvas canvas){
        background.draw(canvas);
        for(final Obstacle obstacle : obstacles)
            obstacle.draw(canvas);
        kitten.draw(canvas);
        if(paused)
            canvas.drawText("Paused", getWidth() / 2, 35, paint);
        if(gameOver)
            canvas.drawText("Game Over", getWidth() / 2, 35, paint);
        canvas.drawText(String.format("%,d", score), 10, 35, paint);
    }
}
