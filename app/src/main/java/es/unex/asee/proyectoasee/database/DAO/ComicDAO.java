package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.ComicEntity;

@Dao
public interface ComicDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComic(ComicEntity comic);

    @Query("SELECT * FROM ComicEntity WHERE id = :id")
    ComicEntity getComic(Integer id);

    @Query("SELECT * FROM ComicEntity WHERE favorite = 1")
    List<ComicEntity> getFavoriteComics();

    @Query("SELECT * FROM ComicEntity WHERE read = 1")
    List<ComicEntity> getReadComics();

    @Query("SELECT * FROM ComicEntity WHERE reading = 1")
    List<ComicEntity> getReadingComics();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateComic(ComicEntity comic);

    @Query("DELETE FROM ComicEntity WHERE id = :id")
    void deleteComic(Integer id);

}
