package es.unex.asee.proyectoasee.database.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.database.DAO.SeriesDAO;
import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;
import es.unex.asee.proyectoasee.database.ROOM.CharacterRoomDatabase;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.pojo.marvel.series.Series;
import es.unex.asee.proyectoasee.pojo.marvel.series.Thumbnail;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class SeriesRepository {

    private static final String apiKey = Utils.apiKey;
    private static final String privateKey = Utils.privateKey;

    //Para consultas de la base de datos local
    private SeriesDAO mSeriesDAO;

    //Para consultas a la Marvel API
    private ApiInterface mApiInterface;


    public SeriesRepository(Application application) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        mSeriesDAO = db.seriesDao();
        mApiInterface = APIClient.getClient().create(ApiInterface.class);
    }


    /***********************
         - DAO METHODS -
     ***********************/

    public SeriesEntity getSeries(Integer id) {
        try {
            return new getSeriesAsyncTask(mSeriesDAO).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getFavoriteSeries() {
        try {
            return new getFavoriteSeriesAsyncTask(mSeriesDAO).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getSeenSeries() {
        try {
            return new getSeenSeriesAsyncTask(mSeriesDAO).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getPendingSeries() {
        try {
            return new getPendingSeriesAsyncTask(mSeriesDAO).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getFollowingSeries() {
        try {
            return new getFollowingSeriesAsyncTask(mSeriesDAO).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSeries(SeriesEntity series) {
        new insertAsyncTask(mSeriesDAO).execute(series);
    }

    public void updateComic(SeriesEntity series) {
        new updateAsyncTask(mSeriesDAO).execute(series);
    }

    public void deleteComic(Integer id) {
        new deleteAsyncTask(mSeriesDAO).execute(id);
    }


    /***********************
        - API METHODS -
     ***********************/

    public LiveData<List<Result>> getAllSeries(final int offset, final int limit) {
        try {
            return new getAllSeriesAsyncTask(mApiInterface).execute(offset,limit).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Result>> getSeriesByName(String name) {
        try {
            return new getSeriesByNameAsyncTask(mApiInterface).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SeriesDetails getSeriesById(Integer id) {
        try {
            return new getSeriesByIdAsyncTask(mApiInterface).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***********************
     - ASYNC TASK SELECTS -
     ***********************/

    private static class getSeriesAsyncTask extends AsyncTask<Integer, Void, SeriesEntity> {

        private SeriesDAO mAsyncTaskDao;

        getSeriesAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected SeriesEntity doInBackground(final Integer... params) {
            return mAsyncTaskDao.getSeries(params[0]);
        }
    }

    private static class getFavoriteSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;

        getFavoriteSeriesAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesEntity> seriesEntities = mAsyncTaskDao.getFavoriteSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesEntity series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getTitle());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }
    }

    private static class getSeenSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;

        getSeenSeriesAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesEntity> seriesEntities = mAsyncTaskDao.getSeenSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesEntity series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getTitle());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }
    }

    private static class getPendingSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;

        getPendingSeriesAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesEntity> seriesEntities = mAsyncTaskDao.getPendingSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesEntity series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getTitle());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }
    }

    private static class getFollowingSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;

        getFollowingSeriesAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesEntity> seriesEntities = mAsyncTaskDao.getFollowingSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesEntity series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getTitle());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }
    }

    private static class getAllSeriesAsyncTask extends AsyncTask<Integer, Void, LiveData<List<Result>>> {

        private ApiInterface mAsyncTaskInterface;
        private MutableLiveData<List<Result>> results;

        getAllSeriesAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected LiveData<List<Result>> doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Call<Series> seriesCall = mAsyncTaskInterface.getSeriesData(ts, apiKey, hashResult, params[0], params[1]);

            results = new MutableLiveData<>();

            Series series;
            try {
                Response<Series> response = seriesCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    series = response.body();
                    results.postValue(series.getData().getResults());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }
    }

    private static class getSeriesByNameAsyncTask extends AsyncTask<String, Void, LiveData<List<Result>>> {

        private ApiInterface mAsyncTaskInterface;
        private MutableLiveData<List<Result>> results;

        getSeriesByNameAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected LiveData<List<Result>> doInBackground(final String... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Call<Series> seriesCall = mAsyncTaskInterface.getSeriesByName(ts, apiKey, hashResult, params[0]);

            results = new MutableLiveData<>();

            Series series;
            try {
                Response<Series> response = seriesCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    series = response.body();
                    results.postValue(series.getData().getResults());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }
    }

    private static class getSeriesByIdAsyncTask extends AsyncTask<Integer, Void, SeriesDetails> {

        private ApiInterface mAsyncTaskInterface;
        private SeriesDetails result;

        getSeriesByIdAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected SeriesDetails doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Integer id = params[0];

            Call<SeriesDetails> seriesCall = mAsyncTaskInterface.getSeriesDetails(id,ts, apiKey, hashResult);

            result = new SeriesDetails();

            try {
                Response<SeriesDetails> response = seriesCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    result = response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }


    /***********************
     - ASYNC TASK INSERTS -
     ***********************/


    private static class insertAsyncTask extends AsyncTask<SeriesEntity, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        insertAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesEntity... params) {
            mAsyncTaskDao.insertSeries(params[0]);
            return null;
        }
    }


    /***********************
     - ASYNC TASK UPDATES -
     ***********************/


    private static class updateAsyncTask extends AsyncTask<SeriesEntity, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        updateAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesEntity... params) {
            mAsyncTaskDao.updateSeries(params[0]);
            return null;
        }
    }

    /***********************
     - ASYNC TASK DELETES -
     ***********************/

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        deleteAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteSeries(params[0]);
            return null;
        }
    }

}
