package es.unex.asee.proyectoasee.database.ROOM;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import es.unex.asee.proyectoasee.database.DAO.CharacterDAO;
import es.unex.asee.proyectoasee.database.DAO.ComicDAO;
import es.unex.asee.proyectoasee.database.DAO.SeriesDAO;
import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;
import es.unex.asee.proyectoasee.database.Entities.ComicEntity;
import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;

@Database(entities = {CharacterEntity.class, ComicEntity.class, SeriesEntity.class}, version = 4)
public abstract class CharacterRoomDatabase extends RoomDatabase {

    public abstract CharacterDAO characterDao();

    public abstract ComicDAO comicDao();

    public abstract SeriesDAO seriesDao();

    private static volatile CharacterRoomDatabase INSTANCE;

    public static CharacterRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CharacterRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CharacterRoomDatabase.class, "CharacterDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
