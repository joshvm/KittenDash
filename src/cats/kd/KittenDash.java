package cats.kd;

import android.app.Activity;
import android.os.Bundle;

public class KittenDash extends Activity{

    public Game game;

    public void onCreate(final Bundle bundle){
        super.onCreate(bundle);

        game = new Game(this);

        setContentView(game);
    }

    public void onPause(){
        super.onPause();
        game.paused = true;
    }

    public void onBackPressed(){
        game.thread.running = false;
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
