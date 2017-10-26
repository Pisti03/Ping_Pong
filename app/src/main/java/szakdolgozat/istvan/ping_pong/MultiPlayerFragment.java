package szakdolgozat.istvan.ping_pong;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultiPlayerFragment extends Fragment implements View.OnClickListener {

    private Button start;
    private EditText bestof, player1, player2;
    private Options options;

    public static MultiPlayerFragment newInstance() {
        return new MultiPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new Options(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_player, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        start = (Button) getView().findViewById(R.id.buttonStart2);
        start.setOnClickListener(this);
        bestof = (EditText) getView().findViewById(R.id.M_BESTOF);
        bestof.setText("5");
        player1 = (EditText) getView().findViewById(R.id.M_PLAYER1);
        player1.setText(options.getUsername());
        player2 = (EditText) getView().findViewById(R.id.M_PLAYER2);
        player2.setText(options.getOpponentName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart2:
                options.setOpponentName(player2.getText().toString());
                Intent intent = new Intent(getActivity(), GameActivity.class);
                Bundle b = new Bundle();
                b.putInt("players", 2); //Your id
                b.putInt("bestof", Integer.parseInt(bestof.getText().toString()));
                b.putString("player1", player1.getText().toString());
                b.putString("player2", player2.getText().toString());
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                break;
        }
    }

}
