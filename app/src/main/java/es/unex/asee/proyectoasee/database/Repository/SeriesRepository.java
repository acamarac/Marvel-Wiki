package es.unex.asee.proyectoasee.database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.asee.proyectoasee.api.client.APIClient;
import es.unex.asee.proyectoasee.database.DAO.SeriesDAO;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesCache;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesData;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesState;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesStateDataJOIN;
import es.unex.asee.proyectoasee.database.ROOM.CharacterRoomDatabase;
import es.unex.asee.proyectoasee.api.interfaces.ApiInterface;
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

    private AsyncResponseInterfaceSeries mCallback;


    public SeriesRepository(Application application, AsyncResponseInterfaceSeries mCallback) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        mSeriesDAO = db.seriesDao();
        mApiInterface = APIClient.getClient().create(ApiInterface.class);
        this.mCallback = mCallback;
    }


    /***********************
         - DAO METHODS -
     ***********************/

    public SeriesState getSeriesState(Integer id) {
        try {
            return new getSeriesStateAsyncTask(mSeriesDAO).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getFavoriteSeries() {
        new getFavoriteSeriesAsyncTask(mSeriesDAO,mCallback).execute();
    }

    public void getSeenSeries() {
        new getSeenSeriesAsyncTask(mSeriesDAO,mCallback).execute();
    }

    public void getPendingSeries() {
        new getPendingSeriesAsyncTask(mSeriesDAO,mCallback).execute();
    }

    public void getFollowingSeries() {
        new getFollowingSeriesAsyncTask(mSeriesDAO,mCallback).execute();
    }

    public void getCacheSeries() {
        new getCacheSeriesAsyncTask(mSeriesDAO, mCallback).execute();
    }

    public void insertSeriesState(SeriesState series) {
        new insertSeriesStateAsyncTask(mSeriesDAO).execute(series);
    }

    public void insertSeriesData(SeriesData series) {
        new insertSeriesDataAsyncTask(mSeriesDAO).execute(series);
    }

    public void insertSeriesCache(SeriesCache series) {
        new insertSeriesCacheAsyncTask(mSeriesDAO).execute(series);
    }

    public void updateSeriesState(SeriesState series) {
        new updateSeriesStateAsyncTask(mSeriesDAO).execute(series);
    }

    public void deleteSeriesState(Integer id) {
        new deleteSeriesStateAsyncTask(mSeriesDAO).execute(id);
    }


    /***********************
        - API METHODS -
     ***********************/

    public void getAllSeries(final int offset, final int limit) {
        new getAllSeriesAsyncTask(mApiInterface,mCallback).execute(offset,limit);
    }

    public void getSeriesByName(String name) {
        new getSeriesByNameAsyncTask(mApiInterface,mCallback).execute(name);
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


    /*********************************
     - INTERFACE PARA LOS CALLBACKS -
     *********************************/
    public interface AsyncResponseInterfaceSeries {
        void sendAllSeries(List<Result> result);
    }


    /***********************
     - ASYNC TASK SELECTS -
     ***********************/

    private static class getSeriesStateAsyncTask extends AsyncTask<Integer, Void, SeriesState> {

        private SeriesDAO mAsyncTaskDao;

        getSeriesStateAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected SeriesState doInBackground(final Integer... params) {
            return mAsyncTaskDao.getSeriesState(params[0]);
        }
    }

    private static class getFavoriteSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;
        private AsyncResponseInterfaceSeries mCallback;

        getFavoriteSeriesAsyncTask(SeriesDAO dao, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesStateDataJOIN> seriesEntities = mAsyncTaskDao.getFavoriteSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesStateDataJOIN series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getName());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getSeenSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;
        private AsyncResponseInterfaceSeries mCallback;

        getSeenSeriesAsyncTask(SeriesDAO dao, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesStateDataJOIN> seriesEntities = mAsyncTaskDao.getSeenSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesStateDataJOIN series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getName());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getPendingSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;
        private AsyncResponseInterfaceSeries mCallback;

        getPendingSeriesAsyncTask(SeriesDAO dao, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesStateDataJOIN> seriesEntities = mAsyncTaskDao.getPendingSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesStateDataJOIN series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getName());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getFollowingSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;
        private AsyncResponseInterfaceSeries mCallback;

        getFollowingSeriesAsyncTask(SeriesDAO dao, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesStateDataJOIN> seriesEntities = mAsyncTaskDao.getFollowingSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesStateDataJOIN series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getName());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getCacheSeriesAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private SeriesDAO mAsyncTaskDao;
        private AsyncResponseInterfaceSeries mCallback;

        getCacheSeriesAsyncTask(SeriesDAO dao, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<SeriesData> seriesEntities = mAsyncTaskDao.getCacheSeries();
            List<Result> results = new ArrayList<>();

            for (SeriesData series : seriesEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(series.getId());
                result.setTitle(series.getName());
                th.setPath(series.getThumbnailPath());
                th.setExtension(series.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getAllSeriesAsyncTask extends AsyncTask<Integer, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterfaceSeries mCallback;

        getAllSeriesAsyncTask(ApiInterface interf, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskInterface = interf;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Call<Series> seriesCall = mAsyncTaskInterface.getSeriesData(ts, apiKey, hashResult, params[0], params[1]);

            results = new ArrayList<>();
            Series series;
            try {
                Response<Series> response = seriesCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    series = response.body();
                    results = series.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
        }
    }

    private static class getSeriesByNameAsyncTask extends AsyncTask<String, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterfaceSeries mCallback;

        getSeriesByNameAsyncTask(ApiInterface interf, AsyncResponseInterfaceSeries mCallback) {
            mAsyncTaskInterface = interf;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(final String... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Call<Series> seriesCall = mAsyncTaskInterface.getSeriesByName(ts, apiKey, hashResult, params[0]);

            results = new ArrayList<>();

            Series series;
            try {
                Response<Series> response = seriesCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    series = response.body();
                    results = series.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllSeries(results);
            super.onPostExecute(results);
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


    private static class insertSeriesStateAsyncTask extends AsyncTask<SeriesState, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        insertSeriesStateAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesState... params) {
            mAsyncTaskDao.insertStateSeries(params[0]);
            return null;
        }
    }

    private static class insertSeriesDataAsyncTask extends AsyncTask<SeriesData, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        insertSeriesDataAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesData... params) {
            mAsyncTaskDao.insertDataSeries(params[0]);
            return null;
        }
    }

    private static class insertSeriesCacheAsyncTask extends AsyncTask<SeriesCache, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        insertSeriesCacheAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesCache... params) {
            mAsyncTaskDao.insertCacheSeries(params[0]);
            return null;
        }
    }

    /***********************
     - ASYNC TASK UPDATES -
     ***********************/


    private static class updateSeriesStateAsyncTask extends AsyncTask<SeriesState, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        updateSeriesStateAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SeriesState... params) {
            mAsyncTaskDao.updateStateSeries(params[0]);
            return null;
        }
    }

    /***********************
     - ASYNC TASK DELETES -
     ***********************/

    private static class deleteSeriesStateAsyncTask extends AsyncTask<Integer, Void, Void> {

        private SeriesDAO mAsyncTaskDao;

        deleteSeriesStateAsyncTask(SeriesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteStateSeries(params[0]);
            return null;
        }
    }

}
