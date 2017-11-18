package szakdolgozat.istvan.ping_pong;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private View view;
    private Options options;
    private EditText userName;
    private SeekBar volume;
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
        volume = (SeekBar) view.findViewById(R.id.SB_VOLUME);
        volume.setMax(100);
        volume.setProgress(options.getVolume());
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                options.setVolume(progressChangedValue);
            }
        });

        sound = (Switch) view.findViewById(R.id.SW_SOUND);
        sound.setChecked(options.getSound());
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                options.setSound(sound.isChecked());
            }
        });

        userName = (EditText) view.findViewById(R.id.ET_NAME);
        userName.setText(options.getUsername());
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!userName.getText().toString().equals(options.getUsername()))
                    options.setUsername(userName.getText().toString());
            }
        });
    }
}
