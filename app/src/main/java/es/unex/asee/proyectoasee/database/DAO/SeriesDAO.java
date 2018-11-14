package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Series.SeriesCache;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesData;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesState;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesStateDataJOIN;

@Dao
public interface SeriesDAO {

    /***************
        - CACHE -
     ***************/
    @Query("SELECT id, name, thumbnailPath, thumbnailExtension FROM SeriesData sd JOIN SeriesCache sc ON sd.id=sc.idSeries")
    List<SeriesData> getCacheSeries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCacheSeries(SeriesCache series);


    /***************
        - STATE -
     ***************/
    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, seen, pending, following " +
            "FROM SeriesData sd JOIN seriesstate ss ON sd.id=ss.idComic " +
            "WHERE favorite = 1")
    List<SeriesStateDataJOIN> getFavoriteSeries();

    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, seen, pending, following " +
            "FROM SeriesData sd JOIN seriesstate ss ON sd.id=ss.idComic " +
            "WHERE seen = 1")
    List<SeriesStateDataJOIN> getSeenSeries();

    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, seen, pending, following " +
            "FROM SeriesData sd JOIN seriesstate ss ON sd.id=ss.idComic " +
            "WHERE pending = 1")
    List<SeriesStateDataJOIN> getPendingSeries();

    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating, seen, pending, following " +
            "FROM SeriesData sd JOIN seriesstate ss ON sd.id=ss.idComic " +
            "WHERE following = 1")
    List<SeriesStateDataJOIN> getFollowingSeries();

    @Query("SELECT * FROM SeriesState WHERE idComic = :id")
    SeriesState getSeriesState(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStateSeries(SeriesState series);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStateSeries(SeriesState series);

    @Query("DELETE FROM SeriesState WHERE idComic = :id")
    void deleteStateSeries(Integer id);

    /***************
        - DATA -
     ***************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataSeries(SeriesData series);

}
