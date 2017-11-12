package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Created by Pisti on 2017. 10. 01..
 */

public class Sound {
    MediaPlayer mediaPlayer = new MediaPlayer();
    Random random = new Random();
    Context context;
    Float volume;

    public Sound(Context context) {
        this.context = context;
        Options options = new Options(context);
        if(options.getSound())
            this.volume = (float) options.getVolume()/ (float) 100;
        else
            this.volume = 0.0f;
    }

    public void play(int rid) {
        stop();
        mediaPlayer = MediaPlayer.create(context, rid);
        mediaPlayer.setVolume(volume,volume);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        mediaPlayer.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setVolume(Float volume){
        mediaPlayer.setVolume(volume, volume);
    }

    void playWallSound(){
        if(random.nextBoolean())
            play(R.raw.wall);
        else
            play(R.raw.wall2);
    }
    void playPlayerSound(){
        if(random.nextBoolean())
            play(R.raw.player);
        else
            play(R.raw.player2);
    }

    void playWinSound(){
        //mediaPlayer.create(context, R.id.asd);
        mediaPlayer.start();
    }
}
