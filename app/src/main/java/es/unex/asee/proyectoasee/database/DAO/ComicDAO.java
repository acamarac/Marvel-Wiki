package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Comics.ComicCache;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicData;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicState;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicStateDataJOIN;

@Dao
public interface ComicDAO {

    /***************
        - CACHE -
     ***************/
    @Query("SELECT id, name, thumbnailPath, thumbnailExtension FROM ComicData cd JOIN ComicCache cc ON cd.id=cc.idComic")
    List<ComicData> getCacheComics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCacheComic(ComicCache comic);

    /***************
        - STATE -
     ***************/
    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, read, reading " +
            "FROM ComicData cd JOIN ComicState cs ON cd.id = cs.idComic " +
            "WHERE favorite = 1")
    List<ComicStateDataJOIN> getFavoriteComics();


    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, read, reading " +
            "FROM ComicData cd JOIN ComicState cs ON cd.id = cs.idComic " +
            "WHERE read = 1")
    List<ComicStateDataJOIN> getReadComics();


    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, read, reading " +
            "FROM ComicData cd JOIN ComicState cs ON cd.id = cs.idComic " +
            "WHERE reading = 1")
    List<ComicStateDataJOIN> getReadingComics();


    @Query("SELECT * FROM ComicState WHERE idComic = :id")
    ComicState getComicState(Integer id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStateComic(ComicState comic);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStateComic(ComicState comic);

    @Query("DELETE FROM ComicState WHERE idComic = :id")
    void deleteStateComic(Integer id);

    /***************
        - DATA -
     ***************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataComic(ComicData comic);

}
