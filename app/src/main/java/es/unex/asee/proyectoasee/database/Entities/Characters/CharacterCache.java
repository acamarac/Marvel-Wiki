package es.unex.asee.proyectoasee.database.Entities.Characters;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "CharacterCache")
public class CharacterCache {

    public CharacterCache(@NonNull Integer idCharacter) {
        this.idCharacter = idCharacter;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idCharacter")
    Integer idCharacter;


    @NonNull
    public Integer getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(@NonNull Integer idCharacter) {
        this.idCharacter = idCharacter;
    }
}
