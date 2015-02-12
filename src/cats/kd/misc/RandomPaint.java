package cats.kd.misc;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import static cats.kd.utils.Utils.color;

public class RandomPaint {

    public final Paint paint;

    public final RectF bounds;

    private long lastUpdate;
    public int delay;

    public RandomPaint(final int delay, final RectF bounds){
        this.delay = delay;
        this.bounds = bounds;

        lastUpdate = System.currentTimeMillis();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(paint.setShader(new LinearGradient(bounds.left, bounds.top, bounds.right, bounds.bottom, color(), color(), Shader.TileMode.MIRROR)));
    }

    public void update(){
        if(System.currentTimeMillis() - lastUpdate < delay)
            return;
        paint.setShader(paint.setShader(new LinearGradient(bounds.left, bounds.top, bounds.right, bounds.bottom, color(), color(), Shader.TileMode.MIRROR)));
        lastUpdate = System.currentTimeMillis();
    }

}
