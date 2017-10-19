package szakdolgozat.istvan.ping_pong;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        /*View v = inflater.inflate(R.layout.fragment_high_scores, container, false);
        ListView lstItems = (ListView)v.findViewById(R.id.matchList);
        ArrayList<Match> matches = helper.getAllMatches();
        for(int i=0; i<matches.size(); i++) System.out.println("Match: " + matches.get(i).toString());
        MatchListAdapter adapter = new MatchListAdapter(getActivity(), R.id.matchList, matches);
        lstItems.setAdapter(adapter);*/
        return inflater.inflate(R.layout.fragment_high_scores, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lstItems = (ListView) getActivity().findViewById(R.id.matchList);
        lstItems.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ArrayList<Match> matches = helper.getAllMatches();
        for(int i=0; i<matches.size(); i++) System.out.println("Match: " + matches.get(i).toString());
        MatchListAdapter adapter = new MatchListAdapter(getActivity(), R.id.matchList, matches);
        lstItems.setAdapter(adapter);
    }
}
