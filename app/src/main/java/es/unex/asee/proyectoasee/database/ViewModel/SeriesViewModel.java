package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;
import es.unex.asee.proyectoasee.database.Repository.SeriesRepository;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;

public class SeriesViewModel extends AndroidViewModel implements SeriesRepository.AsyncResponseInterfaceSeries{

    private SeriesRepository mRepository;
    private MutableLiveData<List<Result>> mAllSeries;

    /***********************
        - CONSTRUCTOR -
     ***********************/
    public SeriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SeriesRepository(application, SeriesViewModel.this);
        mAllSeries = new MutableLiveData<>();
    }

    /***********************
          - GETTERS -
     ***********************/
    public MutableLiveData<List<Result>> getmAllSeries() {
        return mAllSeries;
    }

    /***********************
           - METHODS -
     ***********************/
    public SeriesEntity getSeries(Integer id) {
        return mRepository.getSeries(id);
    }

    public void getFavoriteSeries() {
         mRepository.getFavoriteSeries();
    }

    public void getSeenSeries() {
        mRepository.getSeenSeries();
    }

    public void getFollowingSeries() {
        mRepository.getFollowingSeries();
    }

    public void getPendingSeries() {
        mRepository.getPendingSeries();
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

    public void getAllSeries(Integer offset, Integer limit) {
        mRepository.getAllSeries(offset,limit);
    }

    public void getSeriesByName(String name) {
        mRepository.getSeriesByName(name);
    }

    public SeriesDetails getSeriesById(Integer id) {
        return mRepository.getSeriesById(id);
    }


    /***********************
     - INTERFACE METHOD -
     ***********************/
    @Override
    public void sendAllSeries(List<Result> result) {
        mAllSeries.postValue(result);
    }
}
