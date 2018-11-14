package es.unex.asee.proyectoasee.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterCache;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterData;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterState;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterStateDataJOIN;

@Dao
public interface CharacterDAO {

    @Query("SELECT id, name, thumbnailExtension, thumbnailPath FROM CharacterData cd JOIN CharacterCache cc ON cd.id=cc.idCharacter ORDER BY name")
    List<CharacterData> getCacheData();

    @Query("SELECT id, name, thumbnailPath, thumbnailExtension, favorite, rating FROM CharacterData cd JOIN CharacterState cs on cd.id=cs.idCharacter")
    List<CharacterStateDataJOIN> getAllFavoriteCharacters();

    @Query("SELECT * FROM CharacterState WHERE idCharacter = :id")
    CharacterState getCharacterState(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStateCharacter(CharacterState character);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStateCharacter(CharacterState character);

    @Query("DELETE FROM CharacterState WHERE idCharacter = :id")
    void deleteStateCharacter(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataCharacter(CharacterData character);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCacheCharacter(CharacterCache character);

}
