package es.unex.asee.proyectoasee.fragments.series;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.MainActivity;
import es.unex.asee.proyectoasee.adapters.series.SeriesAdapter;
import es.unex.asee.proyectoasee.database.Entities.Series.SeriesData;
import es.unex.asee.proyectoasee.database.ViewModel.SeriesViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.fragments.preferences.SettingsFragment;

public class SeriesListFragment extends Fragment implements SeriesAdapter.SeriesAdapterListener{

    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SeriesAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;
    private int visibleItemCount, totalItemCount, pastVisibleItems, limit, previousTotal = 0;
    private boolean isLoading = true;

    SearchView mSearchView;

    boolean onSearch = false;

    private SeriesViewModel mSeriesViewModel;

    private SharedPreferences prefs;
    private int limitPreference = 0;

    boolean isInOtherOption = false;

    boolean storeInCache = true;

    RelativeLayout mRelativeLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        setObserver();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Series");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new SeriesAdapter(view.getContext(), SeriesListFragment.this);
        mRecyclerView.setAdapter(adapter);

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        String limitGet = prefs.getString(SettingsFragment.KEY_PREF_LIMIT, "20");
        limitPreference = Integer.valueOf(limitGet);

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_r_layout);

        requestCacheSeries();

        setScrollListener();

        return view;
    }

    public void requestCacheSeries() {
        mSeriesViewModel.getCacheSeries();
    }

    public void requestMoreSeries() {
        mSeriesViewModel.getAllSeries(offset, limitPreference);
    }

    private void setObserver() {

        mSeriesViewModel.getmAllSeries().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                if (results.size() == 0 && !isInOtherOption) {
                    requestMoreSeries();
                } else {
                    if (results.size() == 0 && isInOtherOption) {
                        mRelativeLayout.setVisibility(View.VISIBLE);
                    } else {
                        mRelativeLayout.setVisibility(View.GONE);
                        if (storeInCache) {
                            for (Result result : results) {
                                SeriesData series = new SeriesData(result.getId(), result.getTitle(), result.getThumbnail().getPath(), result.getThumbnail().getExtension());
                                mSeriesViewModel.insertCacheSeries(series);
                            }
                        }
                        adapter.addSeriesPagination(results);
                        offset = offset + results.size();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }
        });

    }

    private void setScrollListener() {

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
                            progressBar.setVisibility(View.VISIBLE);
                            requestMoreSeries();
                            isLoading = true;
                        }
                    }
                }
            }
        });

    }

    private void searchSeriesByName(final String name) {
        adapter.clearList();
        progressBar.setVisibility(View.VISIBLE);
        mSeriesViewModel.getSeriesByName(name);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        mSearchView = (SearchView) item.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isInOtherOption = true;
                storeInCache = false;
                onSearch = true;
                searchSeriesByName(query);
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
                isInOtherOption = false;
                storeInCache = true;
                offset = 0;
                adapter.clearList();
                requestCacheSeries();;
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void sendSeriesId(Integer id, SeriesDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
