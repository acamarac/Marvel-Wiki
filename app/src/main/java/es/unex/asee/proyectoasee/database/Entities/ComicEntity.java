package es.unex.asee.proyectoasee.database.Entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ComicEntity")
public class ComicEntity {

    public ComicEntity(@NonNull Integer id, @NonNull String title, @NonNull String thumbnailPath, @NonNull String thumbnailExtension, @NonNull boolean favorite, @NonNull boolean read, @NonNull boolean reading, @NonNull float rating) {
        this.id = id;
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
        this.favorite = favorite;
        this.read = read;
        this.reading = reading;
        this.rating = rating;
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
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @NonNull
    @ColumnInfo(name = "read")
    private boolean read;

    @NonNull
    @ColumnInfo(name = "reading")
    private boolean reading;

    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;

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

    @NonNull
    public float getRating() {
        return rating;
    }

    public void setRating(@NonNull float rating) {
        this.rating = rating;
    }
}
