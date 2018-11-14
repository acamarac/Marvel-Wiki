package es.unex.asee.proyectoasee.fragments.comics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.MainActivity;
import es.unex.asee.proyectoasee.adapters.comics.ComicsAdapter;
import es.unex.asee.proyectoasee.database.Entities.Comics.ComicData;
import es.unex.asee.proyectoasee.database.ViewModel.ComicViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;
import es.unex.asee.proyectoasee.preferences.SettingsFragment;

public class ComicsListFragment extends Fragment implements ComicsAdapter.ComicsAdapterListener{

    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private ComicsAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;
    private int visibleItemCount, totalItemCount, pastVisibleItems, limit, previousTotal = 0;
    private boolean isLoading = true;

    SearchView mSearchView;

    boolean onSearch = false;

    private ComicViewModel mComicViewModel;

    private SharedPreferences prefs;
    private int limitPreference = 0;

    boolean isInOtherOption = false;

    boolean storeInCache = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Comics");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new ComicsAdapter(view.getContext(), ComicsListFragment.this);
        mRecyclerView.setAdapter(adapter);

        mComicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        String limitGet = prefs.getString(SettingsFragment.KEY_PREF_LIMIT, "20");
        limitPreference = Integer.valueOf(limitGet);

        requestCacheComics();

        mComicViewModel.getmAllComics().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                if (results.size() == 0 && !isInOtherOption) {
                    requestMoreComics();
                } else {
                    for (Result result: results) {
                        ComicData comic = new ComicData(result.getId(), result.getTitle(), result.getThumbnail().getPath(), result.getThumbnail().getExtension());
                        mComicViewModel.insertCacheComic(comic);
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    adapter.addComicsPagination(results);
                    offset += results.size();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

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
                            requestMoreComics();
                            isLoading = true;
                        }
                    }
                }
            }
        });

        return view;
    }

    public void requestCacheComics() {
        mComicViewModel.getCacheComics();
    }

    public void requestMoreComics() {
        mComicViewModel.getAllComics(offset,limitPreference);
    }


    private void searchComicByName(final String name) {
        adapter.clearList();
        mComicViewModel.getComicByName(name);
    }

    private void displayFavComics() {
        adapter.clearList();
        mComicViewModel.getFavoriteComics();
    }

    private void displayReadComic() {
        adapter.clearList();
        mComicViewModel.getReadComics();
    }

    private void displayReadingComic() {
        adapter.clearList();
        mComicViewModel.getReadingComics();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.comic_list_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        mSearchView = (SearchView) item.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                storeInCache = false;
                onSearch = true;
                searchComicByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                storeInCache = true;
                offset = 0;
                adapter.clearList();
                requestCacheComics();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuShowFavorite:
                isInOtherOption = true;
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Favorite Comics");
                displayFavComics();
                return true;
            case R.id.menuShowRead:
                isInOtherOption = true;
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Read Comics");
                displayReadComic();
                return true;
            case R.id.menuShowReading:
                isInOtherOption = true;
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Reading Comics");
                displayReadingComic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void sendComicId(Integer id, ComicDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
