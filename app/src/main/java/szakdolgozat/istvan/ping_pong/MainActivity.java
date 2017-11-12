package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends FragmentActivity {

    private static MainPageAdapter mainPageAdapter;
    private MainButtonsManipulator mainButtonsManipulator;
    private Options options;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        /*Converts an unpacked complex data value holding a dimension to its final floating point value. The two parameters unit and value are as in TYPE_DIMENSION.*/
        int pixelHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MainButtonsManipulator.MODIFIED_HEIGHT_IN_DP, getResources().getDisplayMetrics());
        ImageButton imageButton = (ImageButton) findViewById(R.id.buttonMultiPlayer);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
        mainButtonsManipulator = new MainButtonsManipulator(params.width, params.height, pixelHeight);

        try {
            ImageButton buttonSinglePlayer = (ImageButton) findViewById(R.id.buttonSinglePlayer);
            ImageButton buttonMultiPlayer = (ImageButton) findViewById(R.id.buttonMultiPlayer);
            ImageButton buttonHighScores = (ImageButton) findViewById(R.id.buttonHighScores);
            ImageButton buttonOptions = (ImageButton) findViewById(R.id.buttonOptions);
            ImageButton buttonAbout = (ImageButton) findViewById(R.id.buttonAbout);

            mainButtonsManipulator.addImageButton(buttonSinglePlayer, MainButtonsManipulator.SINGLEPLAYER_POSITION);
            mainButtonsManipulator.addImageButton(buttonMultiPlayer, MainButtonsManipulator.MULTIPLAYER_POSITION);
            mainButtonsManipulator.addImageButton(buttonHighScores, MainButtonsManipulator.HIGHSCORES_POSITION);
            mainButtonsManipulator.addImageButton(buttonOptions, MainButtonsManipulator.OPTIONS_POSITION);
            mainButtonsManipulator.addImageButton(buttonAbout, MainButtonsManipulator.ABOUT_POSITION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainButtonsManipulator.setButtonsUnselected();
                mainButtonsManipulator.setButtonSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(MainButtonsManipulator.SINGLEPLAYER_POSITION);
        options = new Options(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (options.getUsername() == null)
            firstTimeSetup();
    }

    public void firstTimeSetup() {
        double width, height;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        options.setScreenHeight((float) height);
        options.setScreenWidth((float) width);
        options.setSound(true);
        options.setVolume(50);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.usernamepopup, null);


        final EditText editText = (EditText) popupView.findViewById(R.id.ET_USERNAME);
        Button button = (Button) popupView.findViewById(R.id.BT_OK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editText.getText().toString();
                if (userName.isEmpty())
                    userName = "Player";
                options.setUsername(userName);
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupView.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void toSinglePlayer(View v) {
        ViewPager viewPagerGame = (ViewPager) findViewById(R.id.viewPager);
        viewPagerGame.setCurrentItem(MainButtonsManipulator.SINGLEPLAYER_POSITION, false);
    }

    public void toMultiPlayer(View v) {
        ViewPager viewPagerGame = (ViewPager) findViewById(R.id.viewPager);
        viewPagerGame.setCurrentItem(MainButtonsManipulator.MULTIPLAYER_POSITION, false);
    }

    public void toHighScores(View v) {
        ViewPager viewPagerGame = (ViewPager) findViewById(R.id.viewPager);
        viewPagerGame.setCurrentItem(MainButtonsManipulator.HIGHSCORES_POSITION, false);
    }

    public void toOptions(View v) {
        ViewPager viewPagerGame = (ViewPager) findViewById(R.id.viewPager);
        viewPagerGame.setCurrentItem(MainButtonsManipulator.OPTIONS_POSITION, false);
    }

    public void toAbout(View v) {
        ViewPager viewPagerGame = (ViewPager) findViewById(R.id.viewPager);
        viewPagerGame.setCurrentItem(MainButtonsManipulator.ABOUT_POSITION, false);
    }

}
