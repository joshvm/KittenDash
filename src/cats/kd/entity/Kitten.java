package cats.kd.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import cats.kd.Game;
import cats.kd.R;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Kitten extends Entity{

    private final Bitmap bitmap;
    public boolean up;

    public final List<Projectile> projectiles;

    public Kitten(final Game game, final float x, final float y){
        super(game, x, y, 3.5f);

        projectiles = new CopyOnWriteArrayList<>();

        bitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.kitten);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    public void draw(final Canvas canvas){
        canvas.drawBitmap(bitmap, left, top, null);
        for(final Projectile p : projectiles)
            p.draw(canvas);
    }

    public void update(){
        move(up ? NORTH : SOUTH);
        if(game.score >= 100 && game.score % 100 == 0)
            projectiles.add(new Projectile(game, left + width, top + (Projectile.HEIGHT / 2)));
        for(final Projectile p : projectiles)
            p.update();
    }
}
