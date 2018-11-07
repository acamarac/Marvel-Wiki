package es.unex.asee.proyectoasee.database.ROOM;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import es.unex.asee.proyectoasee.database.DAO.CharacterDAO;
import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;

@Database(entities = {CharacterEntity.class}, version = 2)
public abstract class CharacterRoomDatabase extends RoomDatabase {

    public abstract CharacterDAO characterDao();

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
