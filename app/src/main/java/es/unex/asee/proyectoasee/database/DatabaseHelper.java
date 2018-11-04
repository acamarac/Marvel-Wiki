package es.unex.asee.proyectoasee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String dbName = "ASEEMarvelDB.db";
    public static final String CHARACTER_TABLE = "Characters";
    public static final String ID_COLUMN = "id";
    public static final String FAV_COLUMN = "favorite";
    public static final String RATING_COLUM = "rating";

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context,dbName,null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHARACTER_TABLE + "(" +
                ID_COLUMN + " INTEGER PRIMARY KEY, " +
                FAV_COLUMN + " REAL, " +
                RATING_COLUM + " INTEGER"
            + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHARACTER_TABLE);
        onCreate(db);
    }


}
