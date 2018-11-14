package es.unex.asee.proyectoasee.database.Entities.Series;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "SeriesEntityOLD")
public class SeriesEntityOLD {

    public SeriesEntityOLD(@NonNull Integer id, @NonNull String title, @NonNull String thumbnailPath, @NonNull String thumbnailExtension, @NonNull float rating, @NonNull boolean favorite, @NonNull boolean seen, @NonNull boolean pending, @NonNull boolean following) {
        this.id = id;
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
        this.rating = rating;
        this.favorite = favorite;
        this.seen = seen;
        this.pending = pending;
        this.following = following;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "thumbnailPath")
    private String thumbnailPath;

    @NonNull
    @ColumnInfo(name = "thumbnailExtension")
    private String thumbnailExtension;

    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;

    @NonNull
    @ColumnInfo(name = "favorite")
    private boolean favorite;

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
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(@NonNull String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @NonNull
    public String getThumbnailExtension() {
        return thumbnailExtension;
    }

    public void setThumbnailExtension(@NonNull String thumbnailExtension) {
        this.thumbnailExtension = thumbnailExtension;
    }

    @NonNull
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(@NonNull boolean favorite) {
        this.favorite = favorite;
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

    @NonNull
    public float getRating() {
        return rating;
    }

    public void setRating(@NonNull float rating) {
        this.rating = rating;
    }
}
