package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;

@Dao
public interface SeriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeries(SeriesEntity series);

    @Query("SELECT * FROM SeriesEntity WHERE id = :id")
    SeriesEntity getSeries(Integer id);

    @Query("SELECT * FROM SeriesEntity WHERE favorite = 1")
    List<SeriesEntity> getFavoriteSeries();

    @Query("SELECT * FROM SeriesEntity WHERE seen = 1")
    List<SeriesEntity> getSeenSeries();

    @Query("SELECT * FROM SeriesEntity WHERE following = 1")
    List<SeriesEntity> getFollowingSeries();

    @Query("SELECT * FROM SeriesEntity WHERE pending = 1")
    List<SeriesEntity> getPendingSeries();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSeries(SeriesEntity series);

    @Query("DELETE FROM SeriesEntity WHERE id = :id")
    void deleteSeries(Integer id);
}
