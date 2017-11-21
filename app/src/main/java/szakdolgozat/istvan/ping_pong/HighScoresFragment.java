package szakdolgozat.istvan.ping_pong;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HighScoresFragment extends Fragment {

    private SQLiteHelper helper;

    public static HighScoresFragment newInstance() {
        return new HighScoresFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new SQLiteHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_high_scores, container, false);
        TextView total, againstai, multiplayer;
        total = (TextView) v.findViewById(R.id.numOfMatches);
        againstai = (TextView) v.findViewById(R.id.numOfMatchesAI);
        multiplayer = (TextView) v.findViewById(R.id.numOfMatchesMulti);
        int totalNum, aiNum, multiNum;
        totalNum = helper.numberOfMatches();
        aiNum = helper.numberOFMatchesAgainstAi();
        multiNum = totalNum - aiNum;
        total.setText(Integer.toString(totalNum));
        againstai.setText(Integer.toString(aiNum));
        multiplayer.setText(Integer.toString(multiNum));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lstItems = (ListView) getActivity().findViewById(R.id.matchList);
        lstItems.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ArrayList<Match> matches = helper.getFirstXMatches(100);
        MatchListAdapter adapter = new MatchListAdapter(getActivity(), R.id.matchList, matches);
        lstItems.setAdapter(adapter);
    }
}
