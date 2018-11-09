package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;
import es.unex.asee.proyectoasee.database.Repository.SeriesRepository;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;

public class SeriesViewModel extends AndroidViewModel {

    private SeriesRepository mRepository;

    public SeriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SeriesRepository(application);
    }

    public SeriesEntity getSeries(Integer id) {
        return mRepository.getSeries(id);
    }

    public List<Result> getFavoriteSeries() {
        return mRepository.getFavoriteSeries();
    }

    public List<Result> getSeenSeries() {
        return mRepository.getSeenSeries();
    }

    public List<Result> getFollowingSeries() {
        return mRepository.getFollowingSeries();
    }

    public List<Result> getPendingSeries() {
        return mRepository.getPendingSeries();
    }

    public void insertSeries(SeriesEntity series) {
        mRepository.insertSeries(series);
    }

    public void updateSeries(SeriesEntity series) {
        mRepository.updateComic(series);
    }

    public void deleteSeries(Integer id) {
        mRepository.deleteComic(id);
    }

    public LiveData<List<Result>> getAllSeries(Integer offset) {
        return mRepository.getAllSeries(offset);
    }

    public LiveData<List<Result>> getSeriesByName(String name) {
        return mRepository.getSeriesByName(name);
    }

    public SeriesDetails getSeriesById(Integer id) {
        return mRepository.getSeriesById(id);
    }

}
