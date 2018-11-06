package es.unex.asee.proyectoasee.database.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.asee.proyectoasee.database.DAO.CharacterDAO;
import es.unex.asee.proyectoasee.database.Entities.CharacterEntity;
import es.unex.asee.proyectoasee.database.ROOM.CharacterRoomDatabase;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {

    private static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    private static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";

    //Para consultas de la base de datos local
    private CharacterDAO mCharacterDAO;

    //Para consultas a la Marvel API
    private ApiInterface mApiInterface;

    public CharacterRepository(Application application) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        mCharacterDAO = db.characterDao();
        mApiInterface = APIClient.getClient().create(ApiInterface.class);;
    }

    public CharacterEntity getCharacter(Integer id) {
        try {
            return new getCharacterAsyncTask(mCharacterDAO).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertCharacter(CharacterEntity character) {
        new insertAsyncTask(mCharacterDAO).execute(character);
    }

    public void updateCharacter(CharacterEntity character) {
        new updateAsyncTask(mCharacterDAO).execute(character);
    }

    public List<Result> getAllCharacters(int offset) {
        try {
            return new getAllCharactersAsyncTask(mApiInterface).execute(offset).get();
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

    private static class getCharacterAsyncTask extends AsyncTask<Integer, Void, CharacterEntity> {

        private CharacterDAO mAsyncTaskDao;

        getCharacterAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected CharacterEntity doInBackground(final Integer... params) {
            return mAsyncTaskDao.getCharacter(params[0]);
        }
    }

    private static class getAllCharactersAsyncTask extends AsyncTask<Integer, Void, List<Result>> {

        private ApiInterface mAsyncTaskInterface;
        private List<Result> results;

        getAllCharactersAsyncTask(ApiInterface interf) {
            mAsyncTaskInterface = interf;
        }

        @Override
        protected List<Result> doInBackground(final Integer... params) {

            Long tsLong = new Date().getTime();
            String ts = tsLong.toString();

            //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
            String hash = ts + privateKey + apiKey;
            String hashResult = Utils.MD5_Hash(hash);

            Call<Characters> charactersCall = mAsyncTaskInterface.getCharactersData(ts, apiKey, hashResult, params[0]);

            results = new ArrayList<>();

            try {
                Characters characters = charactersCall.execute().body();
                results = characters.getData().getResults();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }
    }


    /***********************
     - ASYNC TASK INSERTS -
     ***********************/


    private static class insertAsyncTask extends AsyncTask<CharacterEntity, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        insertAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterEntity... params) {
            mAsyncTaskDao.insertCharacter(params[0]);
            return null;
        }
    }


    /***********************
     - ASYNC TASK UPDATES -
     ***********************/


    private static class updateAsyncTask extends AsyncTask<CharacterEntity, Void, Void> {

        private CharacterDAO mAsyncTaskDao;

        updateAsyncTask(CharacterDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterEntity... params) {
            mAsyncTaskDao.updateCharacter(params[0]);
            return null;
        }
    }

}
