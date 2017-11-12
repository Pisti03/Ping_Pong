package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pisti on 2017. 10. 01..
 */

public class Options {
    public static final String MYOPTIONS = "MyOptions";
    public static final String USERNAME = "UserName";
    public static final String OPPONENTNAME = "OpponentName";
    public static final String SOUND = "Sound";
    public static final String VOLUME = "Volume";
    public static final String SCREENHEIGHT = "Height";
    public static final String SCREENWIDTH = "Width";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public Options(Context context) {
        this.sharedpreferences = context.getSharedPreferences(MYOPTIONS, Context.MODE_PRIVATE);
        this.editor = sharedpreferences.edit();
    }

    public void setUsername(String userName) {
        editor.putString(USERNAME, userName);
        editor.commit();
    }

    public void setOpponentName(String opponentName) {
        editor.putString(OPPONENTNAME, opponentName);
        editor.commit();
    }

    public void setSound(Boolean sound) {
        editor.putBoolean(SOUND, sound);
        editor.commit();
    }

    public void setVolume(int soundLevel) {
        editor.putInt(VOLUME, soundLevel);
        editor.commit();
    }

    public void setScreenHeight(float height) {
        editor.putFloat(SCREENHEIGHT, height);
        editor.commit();
    }

    public void setScreenWidth(float width) {
        editor.putFloat(SCREENWIDTH, width);
        editor.commit();
    }


    public String getUsername() {
        return sharedpreferences.getString(USERNAME, "Player1");
    }

    public String getOpponentName() {
        return sharedpreferences.getString(OPPONENTNAME, "Player2");
    }

    public Boolean getSound() {
        return sharedpreferences.getBoolean(SOUND, true);
    }

    public int getVolume() {
        return sharedpreferences.getInt(VOLUME, 0);
    }

}
