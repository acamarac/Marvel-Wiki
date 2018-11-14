package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.ComicEntity;
import es.unex.asee.proyectoasee.database.Repository.ComicRepository;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;

public class ComicViewModel extends AndroidViewModel implements ComicRepository.AsyncResponseInterfaceComic{

    private ComicRepository mRepository;
    private MutableLiveData<List<Result>> mAllComics;

    /***********************
        - CONSTRUCTOR -
     ***********************/

    public ComicViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ComicRepository(application, ComicViewModel.this);
        mAllComics = new MutableLiveData<>();
    }

    /***********************
          - GETTERS -
     ***********************/
    public MutableLiveData<List<Result>> getmAllComics() {
        return mAllComics;
    }

    /***********************
          - METHODS -
     ***********************/
    public ComicEntity getComic(Integer id) {
        return mRepository.getComic(id);
    }

    public void getFavoriteComics() {
        mRepository.getFavoriteComics();
    }

    public void getReadComics() {
        mRepository.getReadComics();
    }

    public void getReadingComics() {
        mRepository.getReadingComics();
    }

    public void insertComic(ComicEntity comic) {
        mRepository.insertComic(comic);
    }

    public void updateComic(ComicEntity comic) {
        mRepository.updateComic(comic);
    }

    public void deleteComic(Integer id) {
        mRepository.deleteComic(id);
    }

    public void getAllComics(Integer offset, Integer limit) {
        mRepository.getAllComics(offset,limit);
    }

    public void getComicByName(String name) {
        mRepository.getComicByName(name);
    }

    public ComicDetails getComicById(Integer id) {
        return mRepository.getComicById(id);
    }


    /***********************
     - INTERFACE METHOD -
     ***********************/
    @Override
    public void sendAllComics(List<Result> result) {
        mAllComics.postValue(result);
    }
}
