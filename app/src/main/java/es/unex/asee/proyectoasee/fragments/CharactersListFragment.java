package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.proyectoasee.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.asee.proyectoasee.MainActivity;
import es.unex.asee.proyectoasee.adapters.CharactersAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersListFragment extends Fragment {

    private View view;
    private static final String TAG = "CharactersFragment";
    private static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    private static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";

    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private CharactersAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;
    private int visibleItemCount, totalItemCount, pastVisibleItems, limit, previousTotal = 0;
    private boolean isLoading = true;

    private List<Result> charactersList = new ArrayList<Result>();

    SearchView mSearchView;

    boolean onSearch = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Characters");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);


        requestCharacters();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mGridLayoutManager.getChildCount();
                totalItemCount = mGridLayoutManager.getItemCount();
                pastVisibleItems = mGridLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    } else {
                        if ((totalItemCount - visibleItemCount) <= (pastVisibleItems + limit) && !onSearch) {
                            pagination();
                            isLoading = true;
                        }
                    }
                }
            }
        });

        return view;
    }


    public void requestCharacters() {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        progressBar.setVisibility(View.VISIBLE);
        Call<Characters> charactersCall = apiInterface.getCharactersData(ts, apiKey, hashResult, offset);

        charactersCall.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {

                Characters characters = response.body();

                //If characters is null, then the hash failed and we have to request again

                if (response.code() == 401) {
                    requestCharacters();
                } else {

                    offset = offset + characters.getData().getResults().size();

                    Log.d(TAG, "offset: " + offset);

                    charactersList.addAll(characters.getData().getResults());

                    adapter = new CharactersAdapter(characters.getData().getResults(), view.getContext());
                    mRecyclerView.setAdapter(adapter);

                }



                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }


    private void pagination() {

        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        progressBar.setVisibility(View.VISIBLE);
        Call<Characters> charactersCall = apiInterface.getCharactersData(ts, apiKey, hashResult, offset);

        charactersCall.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {

                if (response.code() == 401) {
                    pagination();
                } else {

                    //We check if response still have characters to show
                    if (response.body().getStatus().equals("Ok")) {

                        List<Result> characters = response.body().getData().getResults();

                        charactersList.addAll(characters);

                        offset = offset + characters.size();
                        adapter.addCharactersPagination(characters);

                        Log.d(TAG, "offset: " + offset);

                    }

                }



                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    private void searchCharacterByName(final String name) {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        progressBar.setVisibility(View.VISIBLE);
        Call<Characters> charactersCall = apiInterface.getCharacterByName(ts, apiKey, hashResult, name);


        charactersCall.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {

                if (response.code() == 401) {
                    searchCharacterByName(name);
                } else {

                    Characters characters = response.body();

                    //If characters is null, then the hash failed and we have to request again

                    if (characters.getCode().equals("InvalidCredentials")) requestCharacters();

                    offset = offset + characters.getData().getResults().size();

                    Log.d(TAG, "offset: " + offset);

                    charactersList.addAll(characters.getData().getResults());

                    adapter = new CharactersAdapter(characters.getData().getResults(), view.getContext());
                    mRecyclerView.setAdapter(adapter);

                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        mSearchView = (SearchView) item.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearch = true;
                searchCharacterByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

}
