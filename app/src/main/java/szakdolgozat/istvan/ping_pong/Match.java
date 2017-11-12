package szakdolgozat.istvan.ping_pong;

import java.sql.Timestamp;

/**
 * Created by Pisti on 2017. 10. 14..
 */

public class Match {
    private int id;
    private String player1;
    private String player2;
    private int player1score, player2score;
    private Timestamp date;

    public Match() {
    }

    public Match(String player1, String player2, int player1score, int player2score, Timestamp date) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1score = player1score;
        this.player2score = player2score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getPlayer1score() {
        return player1score;
    }

    public int getPlayer2score() {
        return player2score;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setPlayer1score(int player1score) {
        this.player1score = player1score;
    }

    public void setPlayer2score(int player2score) {
        this.player2score = player2score;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", player1score=" + player1score +
                ", player2score=" + player2score +
                ", date=" + date +
                '}';
    }
}
