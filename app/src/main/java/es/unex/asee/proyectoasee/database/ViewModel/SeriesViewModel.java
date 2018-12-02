package es.unex.asee.proyectoasee.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.Series.SeriesCache;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesData;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesEntityOLD;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesState;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesStateDataJOIN;
import es.unex.asee.proyectoasee.database.Repository.SeriesRepository;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;

public class SeriesViewModel extends AndroidViewModel implements SeriesRepository.AsyncResponseInterfaceSeries{

    private SeriesRepository mRepository;
    private MutableLiveData<List<Result>> mAllSeriesMLD;
    private LiveData<List<Result>> mAllSeries;

    /***********************
        - CONSTRUCTOR -
     ***********************/
    public SeriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SeriesRepository(application, SeriesViewModel.this);
        mAllSeriesMLD = new MutableLiveData<>();
    }

    /***********************
          - GETTERS -
     ***********************/
    public LiveData<List<Result>> getmAllSeries() {
        mAllSeries = mAllSeriesMLD;
        return mAllSeries;
    }

    /***********************
           - METHODS -
     ***********************/

    public void getCacheSeries() {
        mRepository.getCacheSeries();
    }

    public SeriesState getSeriesState(Integer id) {
        return mRepository.getSeriesState(id);
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

    public void insertCacheSeries(SeriesData series) {
        mRepository.insertSeriesData(series);
        mRepository.insertSeriesCache(new SeriesCache(series.getId()));
    }

    public void insertStateSeries(SeriesStateDataJOIN series) {
        SeriesState state = new SeriesState(series.getId(), series.isFavorite(), series.getRating(), series.isSeen(), series.isPending(), series.isFollowing());
        mRepository.insertSeriesState(state);

        SeriesData data = new SeriesData(series.getId(), series.getName(), series.getThumbnailPath(), series.getThumbnailExtension());
        mRepository.insertSeriesData(data);
    }

    public void updateStateSeries(SeriesStateDataJOIN series) {
        SeriesState state = new SeriesState(series.getId(), series.isFavorite(), series.getRating(), series.isSeen(), series.isPending(), series.isFollowing());
        mRepository.updateSeriesState(state);
    }

    public void deleteStateSeries(Integer id) {
        mRepository.deleteSeriesState(id);
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
        mAllSeriesMLD.postValue(result);
    }
}
