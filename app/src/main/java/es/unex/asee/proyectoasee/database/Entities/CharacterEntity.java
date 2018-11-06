package es.unex.asee.proyectoasee.database.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "CharacterEntity")
public class CharacterEntity {

    public CharacterEntity(Integer id, boolean favorite, float rating) {
        this.id = id;
        this.favorite = favorite;
        this.rating = rating;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

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
