package es.unex.asee.proyectoasee.database.Entities.Series;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "SeriesState")
public class SeriesState {

    public SeriesState(@NonNull Integer idComic, @NonNull boolean favorite, @NonNull float rating, @NonNull boolean seen, @NonNull boolean pending, @NonNull boolean following) {
        this.idComic = idComic;
        this.favorite = favorite;
        this.rating = rating;
        this.seen = seen;
        this.pending = pending;
        this.following = following;
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
    @ColumnInfo(name = "seen")
    private boolean seen;

    @NonNull
    @ColumnInfo(name = "pending")
    private boolean pending;

    @NonNull
    @ColumnInfo(name = "following")
    private boolean following;

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
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(@NonNull boolean seen) {
        this.seen = seen;
    }

    @NonNull
    public boolean isPending() {
        return pending;
    }

    public void setPending(@NonNull boolean pending) {
        this.pending = pending;
    }

    @NonNull
    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(@NonNull boolean following) {
        this.following = following;
    }
}
