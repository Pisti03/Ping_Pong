package szakdolgozat.istvan.ping_pong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

/**
 * Created by Pisti on 2017. 09. 04..
 */

public class MainPageAdapter extends FragmentPagerAdapter{

    private static int NUMBER_OF_BUTTONS = MainButtonsManipulator.NUMBER_OF_BUTTONS;

    public MainPageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case MainButtonsManipulator.SINGLEPLAYER_POSITION:
                return SinglePlayerFragment.newInstance();
            case MainButtonsManipulator.MULTIPLAYER_POSITION:
                return MultiPlayerFragment.newInstance();
            case MainButtonsManipulator.HIGHSCORES_POSITION:
                return HighScoresFragment.newInstance();
            case MainButtonsManipulator.OPTIONS_POSITION:
                return OptionsFragment.newInstance();
            case MainButtonsManipulator.ABOUT_POSITION:
                return AboutFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_BUTTONS;
    }
}
