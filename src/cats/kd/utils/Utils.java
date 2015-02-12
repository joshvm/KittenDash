package cats.kd.utils;

import android.graphics.Color;
import java.util.Random;

public class Utils {

    private static final Random RAND = new Random();

    private Utils(){}

    public static int rand(final int min, final int max){
        return min + RAND.nextInt(max - min + 1);
    }

    public static float rand(final float min, final float max){
        final float n = (float) Math.random();
        return min + (n * (max - min + 1));
    }

    public static int color(){
        return Color.rgb(rand(0, 255), rand(0, 255), rand(0, 255));
    }
}
