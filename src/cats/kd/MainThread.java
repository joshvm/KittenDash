package cats.kd;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

public class MainThread extends Thread implements Runnable{

    public boolean running;

    public final SurfaceHolder holder;
    public final Game game;

    public MainThread(final SurfaceHolder holder, final Game game){
        this.holder = holder;
        this.game = game;
        setPriority(MAX_PRIORITY);
    }

    public void start(){
        running = true;
        super.start();
    }

    private void draw(){
        Canvas canvas = null;
        try{
            canvas = holder.lockCanvas();
            synchronized(holder){
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                game.onDraw(canvas);
            }
        }finally{
            if(canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
    }

    private void update(){
        game.update();
    }

    public void run(){
        while(running){
            if(!game.isShown())
                continue;
            update();
            draw();
        }
    }
}
