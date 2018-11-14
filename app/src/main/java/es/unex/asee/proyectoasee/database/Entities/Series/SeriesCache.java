package es.unex.asee.proyectoasee.database.Entities.Series;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "SeriesCache")
public class SeriesCache {

    public SeriesCache(@NonNull Integer idSeries) {
        this.idSeries = idSeries;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idSeries")
    Integer idSeries;

    @NonNull
    public Integer getIdSeries() {
        return idSeries;
    }

    public void setIdSeries(@NonNull Integer idSeries) {
        this.idSeries = idSeries;
    }
}
