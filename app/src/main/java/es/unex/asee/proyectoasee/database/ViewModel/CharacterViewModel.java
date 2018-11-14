package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;
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
    public CharacterEntity getCharacter(Integer id) {
        return mRepository.getCharacter(id);
    }

    public void getAllFavoriteCharacters() {
        mRepository.getAllFavoriteCharacters();
    }

    public void insertCharacter(CharacterEntity character) {
        mRepository.insertCharacter(character);
    }

    public void updateCharacter(CharacterEntity character) {
        mRepository.updateCharacter(character);
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
        mAllCharacters.postValue(result);
    }

}
