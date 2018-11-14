package es.unex.asee.proyectoasee.database.ROOM;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import es.unex.asee.proyectoasee.database.DAO.CharacterDAO;
import es.unex.asee.proyectoasee.database.DAO.ComicDAO;
import es.unex.asee.proyectoasee.database.DAO.SeriesDAO;
import es.unex.asee.proyectoasee.database.DAO.SeriesDAOOLD;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterCache;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterData;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterState;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicCache;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicData;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicState;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesCache;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesData;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesEntityOLD;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesState;

@Database(entities = {CharacterState.class, CharacterData.class, CharacterCache.class,
        ComicState.class, ComicCache.class, ComicData.class,
        SeriesState.class, SeriesCache.class, SeriesData.class},
        version = 7)
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
