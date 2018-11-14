package es.unex.asee.proyectoasee.database.Entities.Characters;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "CharacterState")
public class CharacterState {

    public CharacterState(@NonNull Integer idCharacter, @NonNull boolean favorite, @NonNull float rating) {
        this.idCharacter = idCharacter;
        this.favorite = favorite;
        this.rating = rating;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idCharacter")
    private Integer idCharacter;

    @NonNull
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;



    @NonNull
    public Integer getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(@NonNull Integer idCharacter) {
        this.idCharacter = idCharacter;
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
