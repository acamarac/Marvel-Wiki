package es.unex.asee.proyectoasee.fragments.series;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import es.unex.asee.proyectoasee.adapters.series.SeriesAdapter;
import es.unex.asee.proyectoasee.database.ViewModel.SeriesViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Series");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new SeriesAdapter(view.getContext(), SeriesListFragment.this);
        mRecyclerView.setAdapter(adapter);

        mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        requestSeries();

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
                            requestSeries();
                            isLoading = true;
                        }
                    }
                }
            }
        });

        return view;
    }

    public void requestSeries() {
        progressBar.setVisibility(View.VISIBLE);
        mSeriesViewModel.getAllSeries(offset).observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                adapter.addSeriesPagination(results);
                offset = offset + results.size();
            }
        });
        progressBar.setVisibility(View.GONE);
    }


    private void searchSeriesByName(final String name) {
        adapter.clearList();
        progressBar.setVisibility(View.VISIBLE);
        mSeriesViewModel.getSeriesByName(name).observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                adapter.addSeriesPagination(results);
                offset = offset + results.size();
            }
        });
        progressBar.setVisibility(View.GONE);
    }


    private void displayFavSeries() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clearList();
        adapter.addSeriesPagination(mSeriesViewModel.getFavoriteSeries());
        progressBar.setVisibility(View.GONE);
    }

    private void displaySeenseries() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clearList();
        adapter.addSeriesPagination(mSeriesViewModel.getSeenSeries());
        progressBar.setVisibility(View.GONE);
    }

    private void displayPendingSeries() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clearList();
        adapter.addSeriesPagination(mSeriesViewModel.getPendingSeries());
        progressBar.setVisibility(View.GONE);
    }

    private void displayFollowingSeries() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clearList();
        adapter.addSeriesPagination(mSeriesViewModel.getFollowingSeries());
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.series_list_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        mSearchView = (SearchView) item.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                offset = 0;
                adapter.clearList();
                requestSeries();
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
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Favorite Series");
                displayFavSeries();
                return true;
            case R.id.menuShowSeen:
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Seen Series");
                displaySeenseries();
                return true;
            case R.id.menuShowPending:
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Pending Series");
                displayPendingSeries();
                return true;
            case R.id.menuShowFollowing:
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Following Series");
                displayFollowingSeries();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void sendSeriesId(Integer id, SeriesDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
