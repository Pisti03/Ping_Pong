package szakdolgozat.istvan.ping_pong;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Istvan Kokas on 2017. 09. 04..
 */

public class MainButtonsManipulator {

    public static final int NUMBER_OF_BUTTONS = 5;
    public static final int MODIFIED_HEIGHT_IN_DP = 60;

    public static final int SINGLEPLAYER_POSITION = 0;
    public static final int MULTIPLAYER_POSITION = 1;
    public static final int HIGHSCORES_POSITION = 2;
    public static final int OPTIONS_POSITION = 3;
    public static final int ABOUT_POSITION = 4;

    private ArrayList<ImageButton> imageButtons;

    private int pixelWidth;
    private int pixelHeight;
    private int pixelHeightSelected;

    public MainButtonsManipulator(int pixelWidth, int pixelHeight, int pixelHeightSelected) {
        imageButtons = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            imageButtons.add(null);
        }

        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.pixelHeightSelected = pixelHeightSelected;
    }

    public void addImageButton(ImageButton imageButton, int position) throws Exception {
        if (position < NUMBER_OF_BUTTONS) {
            imageButtons.set(position, imageButton);
        } else {
            throw new Exception("Exception with main buttons!");
        }
    }

    public void setButtonsUnselected() {
        for (ImageButton imageButton : imageButtons) {
            setButtonUnselected(imageButton);
        }
    }

    private void setButtonUnselected(ImageButton imageButton) {
        RelativeLayout.LayoutParams currentLayoutParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
        currentLayoutParams.height = pixelHeight;
        currentLayoutParams.width = pixelWidth;
        imageButton.setLayoutParams(currentLayoutParams);
    }

    public void setButtonSelected(int position) {
        ImageButton imageButton = imageButtons.get(position);
        RelativeLayout.LayoutParams currentLayoutParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
        currentLayoutParams.height = pixelHeightSelected;
        currentLayoutParams.width = pixelWidth;
        imageButton.setLayoutParams(currentLayoutParams);

    }

}
