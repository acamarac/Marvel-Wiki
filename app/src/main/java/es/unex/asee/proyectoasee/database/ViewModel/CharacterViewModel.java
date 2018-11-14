package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
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
    private MutableLiveData<List<Result>> mAllCharacters;

    /***********************
        - CONSTRUCTOR -
     ***********************/
    public CharacterViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application, CharacterViewModel.this);
        mAllCharacters = new MutableLiveData<>();
    }

    /***********************
           - GETTERS -
     ***********************/
    public MutableLiveData<List<Result>> getmAllCharacters() {
        return mAllCharacters;
    }


    /***********************
          - METHODS -
     ***********************/
    /*public CharacterEntityOLD getCharacter(Integer id) {
        return mRepository.getCharacter(id);
    }*/

    public void getCacheCharacters() {
        mRepository.getCacheCharacters();
    }

    //TODO añadido
    public CharacterState getCharacterState(Integer id) {
        return mRepository.getCharacterState(id);
    }

    public void getAllFavoriteCharacters() {
        mRepository.getAllFavoriteCharacters();
    }

    /**
     * Insertamos un nuevo personaje en la cache, lo que supone insertar sus datos
     * en la tabla CharacterData y su id en la tabla CharacterCache
     * @param character
     */
    public void insertCacheCharacter(CharacterData character) {
        mRepository.insertCharacterData(character);
        mRepository.insertCharacterCache(new CharacterCache(character.getId()));
    }

    /**
     * Insertamos un nuevo estado para un persona, lo que supone insertar sus datos
     * en la tabla CharacterData y su estado en la tabla CharacterState
     * @param character
     */
    public void insertStateCharacter(CharacterStateDataJOIN character) {
        CharacterState state = new CharacterState(character.getId(), character.isFavorite(), character.getRating());
        mRepository.insertCharacterState(state);

        CharacterData data = new CharacterData(character.getId(), character.getName(), character.getThumbnailPath(), character.getThumbnailExtension());
        mRepository.insertCharacterData(data);
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

    /*public void insertCharacter(CharacterEntityOLD character) {
        mRepository.insertCharacter(character);
    }

    public void updateCharacter(CharacterEntityOLD character) {
        mRepository.updateCharacter(character);
    }

    public void deleteCharacter(Integer id) {
        mRepository.deleteCharacter(id);
    }*/



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
        mAllCharacters.postValue(result);
    }

}
