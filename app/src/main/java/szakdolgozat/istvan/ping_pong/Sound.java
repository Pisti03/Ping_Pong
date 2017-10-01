package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Pisti on 2017. 10. 01..
 */

public class Sound {
    MediaPlayer mediaPlayer = new MediaPlayer();
    Context context;

    public Sound(Context context) {
        this.context = context;
    }

    public void setVolume(Float volume){
        mediaPlayer.setVolume(volume, volume);
    }

    void playWallSound(){
        //mediaPlayer.create(context, R.id.asd);
        mediaPlayer.start();
    }
    void playPlayerSound(){
        //mediaPlayer.create(context, R.id.asd);
        mediaPlayer.start();
    }

    void playWinSound(){
        //mediaPlayer.create(context, R.id.asd);
        mediaPlayer.start();
    }
}
