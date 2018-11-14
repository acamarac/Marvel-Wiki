package es.unex.asee.proyectoasee.database.Entities.Comics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ComicCache")
public class ComicCache {

    public ComicCache(@NonNull Integer idComic) {
        this.idComic = idComic;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idComic")
    Integer idComic;


    @NonNull
    public Integer getIdComic() {
        return idComic;
    }

    public void setIdComic(@NonNull Integer idComic) {
        this.idComic = idComic;
    }
}
