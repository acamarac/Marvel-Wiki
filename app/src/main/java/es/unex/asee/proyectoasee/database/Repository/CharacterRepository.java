package es.unex.asee.proyectoasee.database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.asee.proyectoasee.database.DAO.CharacterDAO;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterCache;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterData;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterState;
import es.unex.asee.proyectoasee.database.Entities.Characters.CharacterStateDataJOIN;
import es.unex.asee.proyectoasee.database.ROOM.CharacterRoomDatabase;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Thumbnail;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class CharacterRepository {

    private static final String apiKey = Utils.apiKey;
    private static final String privateKey = Utils.privateKey;

    //Para consultas de la base de datos local
    private CharacterDAO mCharacterDAO;

    //Para consultas a la Marvel API
    private ApiInterface mApiInterface;

    AsyncResponseInterface mCallback;


    public CharacterRepository(Application application, AsyncResponseInterface mCallback) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        mCharacterDAO = db.characterDao();
        mApiInterface = APIClient.getClient().create(ApiInterface.class);
        this.mCallback = mCallback;
    }


    /***********************
     - DAO METHODS -
     ***********************/

    public  CharacterState getCharacterState(Integer id) {
        try {
            return new getCharacterStateAsyncTask(mCharacterDAO).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método que devuelve todos los personajes que se han marcado como favoritos.
     * Lo devuelve como lista de resultados para poder usar el mismo adapter del RecyclerView
     *
     * @return
     */
    public void getAllFavoriteCharacters() {
        new getAllFavoriteCharactersAsyncTask(mCharacterDAO, mCallback).execute();
    }

    public void getCacheCharacters() {
        new getCacheCharactersAsyncTask(mCharacterDAO, mCallback).execute();
    }

    public void insertCharacterState(CharacterState character) {
        new insertCharacterStateAsyncTask(mCharacterDAO).execute(character);
    }

    public void updateCharacterState(CharacterState character) {
        new updateCharacterStateAsyncTask(mCharacterDAO).execute(character);
    }

    public void deleteCharacterState(Integer id) {
        new deleteCharacterStateAsyncTask(mCharacterDAO).execute(id);
    }

    public void insertCharacterData(CharacterData character) {
        new insertCharacterDataAsyncTask(mCharacterDAO).execute(character);
    }

    public void insertCharacterCache(CharacterCache character) {
        new insertCharacterCacheAsyncTask(mCharacterDAO).execute(character);
    }


    /***********************
        - API METHODS -
     ***********************/


    public void getAllCharacters(final int offset, final int limit) {
        new getAllCharactersAsyncTask(mApiInterface, mCallback).execute(offset, limit);
    }


    public void getCharacterByName(String name) {
        new getCharacterByNameAsyncTask(mApiInterface,mCallback).execute(name);
    }

    public CharacterDetails getCharacterById(Integer id) {
        try {
            return new getCharacterByIdAsyncTask(mApiInterface).execute(id).get();
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

    public interface AsyncResponseInterface {
        void sendAllCharacters(List<Result> result);
    }

    private static class getCharacterStateAsyncTask extends AsyncTask<Integer, Void, CharacterState> {

        private CharacterDAO mAsyncTaskDao;

        getCharacterStateAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected CharacterState doInBackground(final Integer... params) {
            return mAsyncTaskDao.getCharacterState(params[0]);
        }
    }

    private static class getAllFavoriteCharactersAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private CharacterDAO mAsyncTaskDao;
        private AsyncResponseInterface mCallback;

        getAllFavoriteCharactersAsyncTask(CharacterDAO dao, AsyncResponseInterface mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<CharacterStateDataJOIN> characterEntities = mAsyncTaskDao.getAllFavoriteCharacters();
            List<Result> results = new ArrayList<>();

            for (CharacterStateDataJOIN character : characterEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(character.getId());
                result.setName(character.getName());
                th.setPath(character.getThumbnailPath());
                th.setExtension(character.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllCharacters(results);
            super.onPostExecute(results);
        }
    }


    private static class getCacheCharactersAsyncTask extends AsyncTask<Void, Void, List<Result>> {

        private CharacterDAO mAsyncTaskDao;
        private AsyncResponseInterface mCallback;

        getCacheCharactersAsyncTask(CharacterDAO dao, AsyncResponseInterface mCallback) {
            mAsyncTaskDao = dao;
            this.mCallback = mCallback;
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            List<CharacterData> characterEntities = mAsyncTaskDao.getCacheData();
            List<Result> results = new ArrayList<>();

            for (CharacterData character : characterEntities) {

                Result result = new Result();
                Thumbnail th = new Thumbnail();

                result.setId(character.getId());
                result.setName(character.getName());
                th.setPath(character.getThumbnailPath());
                th.setExtension(character.getThumbnailExtension());
                result.setThumbnail(th);

                results.add(result);
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            mCallback.sendAllCharacters(results);
            super.onPostExecute(results);
        }
    }


    private static class getAllCharactersAsyncTask extends AsyncTask<Integer, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterface mCallback;

        getAllCharactersAsyncTask(ApiInterface interf, AsyncResponseInterface mCallback) {
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

            Call<Characters> charactersCall = mAsyncTaskInterface.getCharactersData(ts, apiKey, hashResult, params[0], params[1]);

            results = new ArrayList<>();

            Characters characters;
            try {
                Response<Characters> response = charactersCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    characters = response.body();
                    results = characters.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;

        }

        @Override
        protected void onPostExecute(List<Result> list) {
            mCallback.sendAllCharacters(list);
            super.onPostExecute(list);
        }
    }


    private static class getCharacterByNameAsyncTask extends AsyncTask<String, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;
        private AsyncResponseInterface mCallback;

        getCharacterByNameAsyncTask(ApiInterface interf, AsyncResponseInterface mCallback) {
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

            Call<Characters> charactersCall = mAsyncTaskInterface.getCharacterByName(ts, apiKey, hashResult, params[0]);

            results = new ArrayList<>();

            Characters characters;
            try {
                Response<Characters> response = charactersCall.execute();
                if (response.code() == 401) {
                    doInBackground(params);
                } else {
                    characters = response.body();
                    results = characters.getData().getResults();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<Result> list) {
            mCallback.sendAllCharacters(list);
            super.onPostExecute(list);
        }
    }

    private static class getCharacterByIdAsyncTask extends AsyncTask<Integer, Void, CharacterDetails> {

        private ApiInterface mAsyncTaskInterface;
        private CharacterDetails result;

        getCharacterByIdAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected CharacterDetails doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Integer id = params[0];

            Call<CharacterDetails> comicCall = mAsyncTaskInterface.getCharacterDetails(id,ts, apiKey, hashResult);

            result = new CharacterDetails();

            try {
                Response<CharacterDetails> response = comicCall.execute();
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

    private static class insertCharacterStateAsyncTask extends AsyncTask<CharacterState, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        insertCharacterStateAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterState... params) {
            mAsyncTaskDao.insertStateCharacter(params[0]);
            return null;
        }
    }

    private static class insertCharacterDataAsyncTask extends AsyncTask<CharacterData, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        insertCharacterDataAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterData... params) {
            mAsyncTaskDao.insertDataCharacter(params[0]);
            return null;
        }
    }

    private static class insertCharacterCacheAsyncTask extends AsyncTask<CharacterCache, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        insertCharacterCacheAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterCache... params) {
            mAsyncTaskDao.insertCacheCharacter(params[0]);
            return null;
        }
    }


    /***********************
     - ASYNC TASK UPDATES -
     ***********************/

    private static class updateCharacterStateAsyncTask extends AsyncTask<CharacterState, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        updateCharacterStateAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterState... params) {
            mAsyncTaskDao.updateStateCharacter(params[0]);
            return null;
        }
    }

    /***********************
     - ASYNC TASK DELETES -
     ***********************/

    private static class deleteCharacterStateAsyncTask extends AsyncTask<Integer, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        deleteCharacterStateAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteStateCharacter(params[0]);
            return null;
        }
    }

}
