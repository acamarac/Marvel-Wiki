package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.ComicEntity;
import es.unex.asee.proyectoasee.database.Repository.ComicRepository;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;

public class ComicViewModel extends AndroidViewModel {

    private ComicRepository mRepository;

    public ComicViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ComicRepository(application);
    }

    public ComicEntity getComic(Integer id) {
        return mRepository.getComic(id);
    }

    public List<Result> getFavoriteComics() {
        return  mRepository.getFavoriteComics();
    }

    public List<Result> getReadComics() {
        return mRepository.getReadComics();
    }

    public List<Result> getReadingComics() {
        return mRepository.getReadingComics();
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

    public LiveData<List<Result>> getAllComics(Integer offset, Integer limit) {
        return mRepository.getAllComics(offset,limit);
    }

    public LiveData<List<Result>> getComicByName(String name) {
        return mRepository.getComicByName(name);
    }

    public ComicDetails getComicById(Integer id) {
        return mRepository.getComicById(id);
    }

}
