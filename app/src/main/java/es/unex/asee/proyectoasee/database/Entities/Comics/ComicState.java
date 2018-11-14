package es.unex.asee.proyectoasee.database.Entities.Comics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ComicState")
public class ComicState {

    public ComicState(@NonNull Integer idComic, @NonNull boolean favorite, @NonNull float rating, @NonNull boolean read, @NonNull boolean reading) {
        this.idComic = idComic;
        this.favorite = favorite;
        this.rating = rating;
        this.read = read;
        this.reading = reading;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idComic")
    private Integer idComic;

    @NonNull
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;

    @NonNull
    @ColumnInfo(name = "read")
    private boolean read;

    @NonNull
    @ColumnInfo(name = "reading")
    private boolean reading;


    @NonNull
    public Integer getIdComic() {
        return idComic;
    }

    public void setIdComic(@NonNull Integer idComic) {
        this.idComic = idComic;
    }

    @NonNull
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(@NonNull boolean favorite) {
        this.favorite = favorite;
    }

    @NonNull
    public float getRating() {
        return rating;
    }

    public void setRating(@NonNull float rating) {
        this.rating = rating;
    }

    @NonNull
    public boolean isRead() {
        return read;
    }

    public void setRead(@NonNull boolean read) {
        this.read = read;
    }

    @NonNull
    public boolean isReading() {
        return reading;
    }

    public void setReading(@NonNull boolean reading) {
        this.reading = reading;
    }
}
