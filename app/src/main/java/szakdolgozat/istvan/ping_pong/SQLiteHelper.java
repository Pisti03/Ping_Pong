package szakdolgozat.istvan.ping_pong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pisti on 2017. 10. 14..
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MatchDB";
    public static final String MATCH_TABLE_NAME = "Matches";
    public static final String MATCH_COLUMN_ID = "id";
    public static final String MATCH_COLUMN_PLAYER1 = "Player1";
    public static final String MATCH_COLUMN_PLAYER2 = "Player2";
    public static final String MATCH_COLUMN_WINNER = "Winner";
    public static final String MATCH_COLUMN_SCORE = "Score";
    public static final String MATCH_COLUMN_DATE = "Date";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE " + MATCH_TABLE_NAME + "  ( " +
                MATCH_COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MATCH_COLUMN_PLAYER1 + " TEXT, "    +
                MATCH_COLUMN_PLAYER2 + " TEXT, "    +
                MATCH_COLUMN_WINNER  + " INTEGER, "    +
                MATCH_COLUMN_SCORE   + " INTEGER, " +
                MATCH_COLUMN_DATE    + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE_NAME);
        this.onCreate(db);
    }

    public void insertMatch(String player1, String player2, int winner, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MATCH_COLUMN_PLAYER1, player1);
        contentValues.put(MATCH_COLUMN_PLAYER2, player2);
        contentValues.put(MATCH_COLUMN_WINNER, winner);
        contentValues.put(MATCH_COLUMN_SCORE, score);

        db.insert(MATCH_TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateMatch(Integer id, String player1, String player2, int winner, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MATCH_COLUMN_PLAYER1, player1);
        contentValues.put(MATCH_COLUMN_PLAYER2, player2);
        contentValues.put(MATCH_COLUMN_WINNER, winner);
        contentValues.put(MATCH_COLUMN_SCORE, score);
        db.update(MATCH_TABLE_NAME, contentValues, MATCH_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        db.close();
    }

    public void deleteMatch(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MATCH_TABLE_NAME,
                MATCH_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        db.close();
    }

    public Match getMatch(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_ID + "=?", new String[]{Integer.toString(id)});

        Match match = new Match();
        match.setId(Integer.parseInt(cursor.getString(0)));
        match.setPlayer1(cursor.getString(1));
        match.setPlayer2(cursor.getString(2));
        match.setWinner(Integer.parseInt(cursor.getString(3)));
        match.setScore(Integer.parseInt(cursor.getString(4)));
        match.setDate(Timestamp.valueOf(cursor.getString(5)));
        db.close();
        return match;
    }

    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> matches = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME, null );

        Match match;

        if (cursor.moveToFirst()) {
            do {
                match = new Match();
                match.setId(Integer.parseInt(cursor.getString(0)));
                match.setPlayer1(cursor.getString(1));
                match.setPlayer2(cursor.getString(2));
                match.setWinner(Integer.parseInt(cursor.getString(3)));
                match.setScore(Integer.parseInt(cursor.getString(4)));
                match.setDate(Timestamp.valueOf(cursor.getString(5)));

                matches.add(match);
            } while (cursor.moveToNext());
        }
        db.close();
        return matches;
    }

    public ArrayList<Match> getFirstXMatches(int i) {
        ArrayList<Match> matches = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " ORDER BY " + MATCH_COLUMN_DATE + " DESC LIMIT " + i, null );

        Match match;

        if (cursor.moveToFirst()) {
            do {
                match = new Match();
                match.setId(Integer.parseInt(cursor.getString(0)));
                match.setPlayer1(cursor.getString(1));
                match.setPlayer2(cursor.getString(2));
                match.setWinner(Integer.parseInt(cursor.getString(3)));
                match.setScore(Integer.parseInt(cursor.getString(4)));
                match.setDate(Timestamp.valueOf(cursor.getString(5)));

                matches.add(match);
            } while (cursor.moveToNext());
        }
        db.close();
        return matches;
    }

    public int numberOfMatches() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MATCH_TABLE_NAME);
        db.close();
        return numRows;
    }

    public int numberOFMatchesAgainstAi() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MATCH_TABLE_NAME, MATCH_COLUMN_PLAYER2 + "='AI'");
        db.close();
        return numRows;
    }

}
