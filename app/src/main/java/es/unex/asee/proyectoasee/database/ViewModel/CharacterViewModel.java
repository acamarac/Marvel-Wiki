package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;
import es.unex.asee.proyectoasee.database.Repository.CharacterRepository;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;

public class CharacterViewModel extends AndroidViewModel {

    private CharacterRepository mRepository;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application);
    }

    public CharacterEntity getCharacter(Integer id) {
        return mRepository.getCharacter(id);
    }

    public List<Result> getAllFavoriteCharacters() {
        return mRepository.getAllFavoriteCharacters();
    }

    public void insertCharacter(CharacterEntity character) {
        mRepository.insertCharacter(character);
    }

    public void updateCharacter(CharacterEntity character) {
        mRepository.updateCharacter(character);
    }

    public LiveData<List<Result>> getAllCharacters(Integer offset) {
        return mRepository.getAllCharacters(offset);
    }

    public LiveData<List<Result>> getCharacterByName(String name) {
        return mRepository.getCharacterByName(name);
    }

    public CharacterDetails getCharacterById(Integer id) {
        return mRepository.getCharacterById(id);
    }

}
