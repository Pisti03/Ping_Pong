package szakdolgozat.istvan.ping_pong;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView wscore = (TextView) convertView.findViewById(R.id.wscore);
        TextView lscore = (TextView) convertView.findViewById(R.id.lscore);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Match match = getItem(position);
        winner.setText(match.getPlayer1());
        loser.setText(match.getPlayer2());
        date.setText(format.format(match.getDate()));

        if(match.getPlayer1score() > match.getPlayer2score()) {
            winner.setText(match.getPlayer1());
            winner.setTextColor(Color.BLUE);
            wscore.setText(Integer.toString(match.getPlayer1score()));
            wscore.setTextColor(Color.BLUE);
            loser.setText(match.getPlayer2());
            loser.setTextColor(Color.RED);
            lscore.setText(Integer.toString(match.getPlayer2score()));
            lscore.setTextColor(Color.RED);
        } else {
            winner.setText(match.getPlayer2());
            winner.setTextColor(Color.RED);
            wscore.setText(Integer.toString(match.getPlayer2score()));
            wscore.setTextColor(Color.RED);
            loser.setText(match.getPlayer1());
            loser.setTextColor(Color.BLUE);
            lscore.setText(Integer.toString(match.getPlayer1score()));
            lscore.setTextColor(Color.BLUE);
        }
        return convertView;
    }
}