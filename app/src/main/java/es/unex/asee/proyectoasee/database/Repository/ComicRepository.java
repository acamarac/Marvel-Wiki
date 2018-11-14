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
import es.unex.asee.proyectoasee.database.DAO.ComicDAO;
import es.unex.asee.proyectoasee.database.Entities.ComicEntity;
import es.unex.asee.proyectoasee.database.ROOM.CharacterRoomDatabase;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Comics;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Thumbnail;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class ComicRepository {

    private static final String apiKey = Utils.apiKey;
    private static final String privateKey = Utils.privateKey;

    //Para consultas de la base de datos local
    private ComicDAO mComicDAO;

    //Para consultas a la Marvel API
    private ApiInterface mApiInterface;

    private AsyncResponseInterfaceComic mCallback;


    public ComicRepository(Application application, AsyncResponseInterfaceComic mCallback) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        mComicDAO = db.comicDao();
        mApiInterface = APIClient.getClient().create(ApiInterface.class);
        this.mCallback = mCallback;
    }


    /***********************
         - DAO METHODS -
     ***********************/

    public ComicEntity getComic(Integer id)  {
        try {
            return new getComicAsyncTask(mComicDAO).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getFavoriteComics()  {
        new getFavoriteComicsAsyncTask(mComicDAO, mCallback).execute();
    }

    public void getReadComics() {
        new getReadComicsAsyncTask(mComicDAO, mCallback).execute();
    }

    public void getReadingComics()  {
        new getReadingComicsAsyncTask(mComicDAO, mCallback).execute();
    }

    public void insertComic(ComicEntity comic) {
        new insertAsyncTask(mComicDAO).execute(comic);
    }

    public void updateComic(ComicEntity comic) {
        new updateAsyncTask(mComicDAO).execute(comic);
    }

    public void deleteComic(Integer id) {
        new deleteComicAsyncTask(mComicDAO).execute(id);
    }

    /***********************
         - API METHODS -
     ***********************/

    public void getAllComics(final int offset, final int limit) {
        new getAllComicsAsyncTask(mApiInterface,mCallback).execute(offset,limit);
    }

    public void getComicByName(String name) {
        new getComicByNameAsyncTask(mApiInterface,mCallback).execute(name);
    }

    public ComicDetails getComicById(Integer id) {
        try {
            return new getComicByIdAsyncTask(mApiInterface).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /****************************
     - INTERFACE PARA CALLBACKS -
     ****************************/
    public interface AsyncResponseInterfaceComic {
        void sendAllComics(List<Result> result);
    }

    /***********************
     - ASYNC TASK SELECTS -
     ***********************/

    private static class getComicAsyncTask extends AsyncTask<Integer, Void, ComicEntity> {

        private ComicDAO mAsyncTaskDao;

        getComicAsyncTask(ComicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected ComicEntity doInBackground(final Integer... params) {
            return mAsyncTaskDao.getComic(params[0]);
        }
    }

    private static class getFavoriteComicsAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private ComicDAO mAsyncTaskDao;
        private AsyncResponseInterfaceComic mCallback;

        getFavoriteComicsAsyncTask(ComicDAO dao, AsyncResponseInterfaceComic mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<ComicEntity> comicEntities = mAsyncTaskDao.getFavoriteComics();
            List<Result> results = new ArrayList<>();

            for (ComicEntity comic : comicEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(comic.getId());
                result.setTitle(comic.getTitle());
                th.setPath(comic.getThumbnailPath());
                th.setExtension(comic.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllComics(results);
            super.onPostExecute(results);
        }
    }

    private static class getReadComicsAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private ComicDAO mAsyncTaskDao;
        private AsyncResponseInterfaceComic mCallback;

        getReadComicsAsyncTask(ComicDAO dao, AsyncResponseInterfaceComic mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<ComicEntity> comicEntities = mAsyncTaskDao.getReadComics();
            List<Result> results = new ArrayList<>();

            for (ComicEntity comic : comicEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(comic.getId());
                result.setTitle(comic.getTitle());
                th.setPath(comic.getThumbnailPath());
                th.setExtension(comic.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllComics(results);
            super.onPostExecute(results);
        }
    }

    private static class getReadingComicsAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private ComicDAO mAsyncTaskDao;
        private AsyncResponseInterfaceComic mCallback;

        getReadingComicsAsyncTask(ComicDAO dao, AsyncResponseInterfaceComic mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<ComicEntity> comicEntities = mAsyncTaskDao.getReadingComics();
            List<Result> results = new ArrayList<>();

            for (ComicEntity comic : comicEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(comic.getId());
                result.setTitle(comic.getTitle());
                th.setPath(comic.getThumbnailPath());
                th.setExtension(comic.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllComics(results);
            super.onPostExecute(results);
        }
    }


    private static class getAllComicsAsyncTask extends AsyncTask<Integer, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterfaceComic mCallback;

        getAllComicsAsyncTask(ApiInterface interf, AsyncResponseInterfaceComic mCallback) {
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

            Call<Comics> comicsCall = mAsyncTaskInterface.getComicsData(ts, apiKey, hashResult, params[0], params[1]);

            results = new ArrayList<>();

            Comics comics;
            try {
                Response<Comics> response = comicsCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    comics = response.body();
                    results = comics.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllComics(results);
            super.onPostExecute(results);
        }
    }

    private static class getComicByNameAsyncTask extends AsyncTask<String, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterfaceComic mCallback;

        getComicByNameAsyncTask(ApiInterface interf, AsyncResponseInterfaceComic mCallback) {
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

            Call<Comics> charactersCall = mAsyncTaskInterface.getComicByName(ts, apiKey, hashResult, params[0]);

            results = new ArrayList<>();

            Comics comics;
            try {
                Response<Comics> response = charactersCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    comics = response.body();
                    results = comics.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllComics(results);
            super.onPostExecute(results);
        }
    }


    private static class getComicByIdAsyncTask extends AsyncTask<Integer, Void, ComicDetails> {

        private ApiInterface mAsyncTaskInterface;
        private ComicDetails result;

        getComicByIdAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected ComicDetails doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Integer id = params[0];

            Call<ComicDetails> comicCall = mAsyncTaskInterface.getComicDetails(id,ts, apiKey, hashResult);

            result = new ComicDetails();

            try {
                Response<ComicDetails> response = comicCall.execute();
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


    private static class insertAsyncTask extends AsyncTask<ComicEntity, Void, Void> {

        private ComicDAO mAsyncTaskDao;

        insertAsyncTask(ComicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ComicEntity... params) {
            mAsyncTaskDao.insertComic(params[0]);
            return null;
        }
    }


    /***********************
     - ASYNC TASK UPDATES -
     ***********************/


    private static class updateAsyncTask extends AsyncTask<ComicEntity, Void, Void> {

        private ComicDAO mAsyncTaskDao;

        updateAsyncTask(ComicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ComicEntity... params) {
            mAsyncTaskDao.updateComic(params[0]);
            return null;
        }
    }

    /***********************
     - ASYNC TASK DELETES -
     ***********************/

    private static class deleteComicAsyncTask extends AsyncTask<Integer, Void, Void> {

        private ComicDAO mAsyncTaskDao;

        deleteComicAsyncTask(ComicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteComic(params[0]);
            return null;
        }
    }

}
