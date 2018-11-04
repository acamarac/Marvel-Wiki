package es.unex.asee.proyectoasee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import es.unex.asee.proyectoasee.databasePOJO.CharacterDb;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        //context.deleteDatabase(DatabaseHelper.dbName);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public void insertCharacterInformation(CharacterDb character) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ID_COLUMN, character.getId());
        contentValue.put(DatabaseHelper.FAV_COLUMN, character.getFavorite());
        contentValue.put(DatabaseHelper.RATING_COLUM, character.getRating());
        database.insert(DatabaseHelper.CHARACTER_TABLE, null, contentValue);
    }


    public CharacterDb getCharacterInformation(long id) {
        String query = "SELECT * FROM " + DatabaseHelper.CHARACTER_TABLE + " WHERE " + DatabaseHelper.ID_COLUMN + "= '" + id +"';";

        CharacterDb result = null;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = new CharacterDb();
            result.setId(cursor.getLong(0));
            result.setFavorite(cursor.getLong(1));
            result.setRating(cursor.getFloat(2));
        }

        return result;
    }


    public int updateCharacterInformation(CharacterDb character) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID_COLUMN, character.getId());
        contentValues.put(DatabaseHelper.FAV_COLUMN, character.getFavorite());
        contentValues.put(DatabaseHelper.RATING_COLUM, character.getRating());

        int result = database.update(DatabaseHelper.CHARACTER_TABLE, contentValues, DatabaseHelper.ID_COLUMN + " = " + character.getId(), null);

        return result;
    }


    public void deleteCharacterInformation(long id) {
        database.delete(DatabaseHelper.CHARACTER_TABLE, DatabaseHelper.ID_COLUMN + "=" + id, null);
    }

}
