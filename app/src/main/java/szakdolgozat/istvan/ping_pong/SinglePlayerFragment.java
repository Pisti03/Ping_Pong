package szakdolgozat.istvan.ping_pong;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class SinglePlayerFragment extends Fragment implements View.OnClickListener{

    private Button start;

    public static SinglePlayerFragment newInstance() {
        return new SinglePlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_player, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        start = (Button) getView().findViewById(R.id.buttonStart);
        start.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                this.startActivity(new Intent(getActivity(), GameActivity.class));
            break;
        }
    }

}
