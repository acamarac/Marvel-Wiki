package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Comics.ComicCache;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicData;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicState;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicStateDataJOIN;
import es.unex.asee.proyectoasee.database.Repository.ComicRepository;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;

public class ComicViewModel extends AndroidViewModel implements ComicRepository.AsyncResponseInterfaceComic{

    private ComicRepository mRepository;
    private MutableLiveData<List<Result>> mAllComicsMLD;
    private LiveData<List<Result>> mAllComics;

    /***********************
        - CONSTRUCTOR -
     ***********************/

    public ComicViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ComicRepository(application, ComicViewModel.this);
        mAllComicsMLD = new MutableLiveData<>();
    }

    /***********************
          - GETTERS -
     ***********************/
    public LiveData<List<Result>> getmAllComics() {
        mAllComics = mAllComicsMLD;
        return mAllComics;
    }

    /***********************
          - METHODS -
     ***********************/
    public void getCacheComics() {
        mRepository.getCacheComics();
    }

    public ComicState getComicState(Integer id) {
        return mRepository.getComicState(id);
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

    public void insertCacheComic(ComicData comic) {
        mRepository.insertCacheComic(comic);
    }

    public void insertStateComic(ComicStateDataJOIN comic) {
        mRepository.insertStateComic(comic);
    }

    public void updateStateComic(ComicStateDataJOIN comic) {
        ComicState state = new ComicState(comic.getId(), comic.isFavorite(), comic.getRating(), comic.isRead(), comic.isReading());
        mRepository.updateComicState(state);
    }

    public void deleteStateComic(Integer id) {
        mRepository.deleteComicState(id);
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
        mAllComicsMLD.postValue(result);
    }
}
