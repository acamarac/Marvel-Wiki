package es.unex.asee.proyectoasee.database.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "CharacterEntity")
public class CharacterEntity {

    public CharacterEntity(Integer id, String name, String thumbnailPath, String thumbnailExtension, boolean favorite, float rating) {
        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
        this.favorite = favorite;
        this.rating = rating;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

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
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
    public float getRating() {
        return rating;
    }

    public void setRating(@NonNull float rating) {
        this.rating = rating;
    }
}
