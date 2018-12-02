package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterCache;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterData;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterState;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterStateDataJOIN;
import es.unex.asee.proyectoasee.database.Repository.CharacterRepository;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;

public class CharacterViewModel extends AndroidViewModel implements CharacterRepository.AsyncResponseInterface{

    private CharacterRepository mRepository;
    private MutableLiveData<List<Result>> mAllCharactersMLD;
    private LiveData<List<Result>> mAllCharacters;

    /***********************
        - CONSTRUCTOR -
     ***********************/
    public CharacterViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application, CharacterViewModel.this);
        mAllCharactersMLD = new MutableLiveData<>();
    }

    /***********************
           - GETTERS -
     ***********************/
    public LiveData<List<Result>> getmAllCharacters() {
        mAllCharacters = mAllCharactersMLD;
        return mAllCharacters;
    }


    /***********************
          - METHODS -
     ***********************/
    public void getCacheCharacters() {
        mRepository.getCacheCharacters();
    }

    public CharacterState getCharacterState(Integer id) {
        return mRepository.getCharacterState(id);
    }

    public void getAllFavoriteCharacters() {
        mRepository.getAllFavoriteCharacters();
    }

    /**
     * Insertamos un nuevo personaje en la cache
     * @param character
     */
    public void insertCacheCharacter(CharacterData character) {
        mRepository.insertCacheCharacter(character);
    }

    /**
     * Insertamos un nuevo estado para un personaje
     * @param character
     */
    public void insertStateCharacter(CharacterStateDataJOIN character) {
        mRepository.insertStateCharacter(character);
    }

    /**
     * En este caso, los datos del personaje ya existen en la tabla CharacterData,
     * por lo que solo actualizamos characterState
     * @param character
     */
    public void updateStateCharacter(CharacterStateDataJOIN character) {
        CharacterState state = new CharacterState(character.getId(), character.isFavorite(), character.getRating());
        mRepository.updateCharacterState(state);
    }

    /**
     * Borramos el personaje de la tabla CharacterState, no lo borramos de CharacterData
     * por si estuviera también en caché
     * @param id
     */
    public void deleteStateCharacter(Integer id) {
        mRepository.deleteCharacterState(id);
    }

    public void getAllCharacters(Integer offset, Integer limit) {
        mRepository.getAllCharacters(offset, limit);
    }

    public void getCharacterByName(String name) {
        mRepository.getCharacterByName(name);
    }

    public CharacterDetails getCharacterById(Integer id) {
        return mRepository.getCharacterById(id);
    }


    /***********************
       - INTERFACE METHOD -
     ***********************/
    @Override
    public void sendAllCharacters(List<Result> result) {
        mAllCharactersMLD.postValue(result);
    }

}
