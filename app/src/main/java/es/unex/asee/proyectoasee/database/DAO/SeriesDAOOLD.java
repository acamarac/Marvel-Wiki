package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Series.SeriesEntityOLD;

@Dao
public interface SeriesDAOOLD {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeries(SeriesEntityOLD series);

    @Query("SELECT * FROM SeriesEntityOLD WHERE id = :id")
    SeriesEntityOLD getSeries(Integer id);

    @Query("SELECT * FROM SeriesEntityOLD WHERE favorite = 1")
    List<SeriesEntityOLD> getFavoriteSeries();

    @Query("SELECT * FROM SeriesEntityOLD WHERE seen = 1")
    List<SeriesEntityOLD> getSeenSeries();

    @Query("SELECT * FROM SeriesEntityOLD WHERE following = 1")
    List<SeriesEntityOLD> getFollowingSeries();

    @Query("SELECT * FROM SeriesEntityOLD WHERE pending = 1")
    List<SeriesEntityOLD> getPendingSeries();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSeries(SeriesEntityOLD series);

    @Query("DELETE FROM SeriesEntityOLD WHERE id = :id")
    void deleteSeries(Integer id);
}
