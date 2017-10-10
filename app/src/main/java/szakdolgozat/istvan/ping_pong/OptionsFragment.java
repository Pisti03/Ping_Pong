package szakdolgozat.istvan.ping_pong;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Options options;
    private int volumeLevel;
    private EditText userName;
    private SeekBar volume;
    private Button save;
    private Switch sound;

    public static OptionsFragment newInstance() {
        return new OptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new Options(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        save = (Button) view.findViewById(R.id.BT_SAVE);
        save.setOnClickListener(this);
        volume = (SeekBar) view.findViewById(R.id.SB_VOLUME);
        volume.setMax(100);
        volumeLevel = options.getVolume();
        volume.setProgress(volumeLevel);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                volumeLevel = progressChangedValue;
            }
        });

        sound = (Switch) view.findViewById(R.id.SW_SOUND);
        sound.setChecked(options.getSound());
        userName = (EditText) view.findViewById(R.id.ET_NAME);
        userName.setText(options.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BT_SAVE:
                options.setVolume(volumeLevel);
                options.setUsername(userName.getText().toString());
                options.setSound(sound.isChecked());
                break;
        }
    }
}
