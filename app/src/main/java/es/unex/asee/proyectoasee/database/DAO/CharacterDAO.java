package es.unex.asee.proyectoasee.database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;

@Dao
public interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntity character);

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    CharacterEntity getCharacter(Integer id);

    @Query("SELECT * FROM CharacterEntity WHERE favorite = 1")
    List<CharacterEntity> getAllFavoriteCharacters();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCharacter(CharacterEntity character);

    @Query("DELETE FROM CharacterEntity WHERE id = :id")
    void deleteCharacter(Integer id);

}
