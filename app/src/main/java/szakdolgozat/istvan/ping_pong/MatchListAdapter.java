package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pisti on 2017. 10. 17..
 */

public class MatchListAdapter extends ArrayAdapter<Match> {

    SimpleDateFormat format;

    public MatchListAdapter(Context context, int resource, ArrayList<Match> values) {
        super(context, resource, values);
        this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.match_history_line, parent, false);
        }

        TextView winner = (TextView) convertView.findViewById(R.id.winner);
        TextView loser = (TextView) convertView.findViewById(R.id.loser);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Match match = getItem(position);
        winner.setText(match.getPlayer1());
        loser.setText(match.getPlayer2());
        score.setText(Integer.toString(match.getScore()));
        date.setText(format.format(match.getDate()));

        if(match.getWinner() == 1) {
            winner.setText(match.getPlayer1());
            winner.setTextColor(Color.GREEN);
            loser.setText(match.getPlayer2());
        } else {
            winner.setText(match.getPlayer2());
            winner.setTextColor(Color.GREEN);
            loser.setText(match.getPlayer1());
        }
        return convertView;
    }
}