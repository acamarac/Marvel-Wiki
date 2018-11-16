package es.unex.asee.proyectoasee.fragments.series;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.MainActivity;
import es.unex.asee.proyectoasee.adapters.series.SeriesAdapter;
import es.unex.asee.proyectoasee.database.ViewModel.SeriesViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;

public class SeriesFollowingFragment extends Fragment implements SeriesAdapter.SeriesAdapterListener {

    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SeriesAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;
    private int visibleItemCount, totalItemCount, pastVisibleItems, limit, previousTotal = 0;
    private boolean isLoading = true;

    boolean onSearch = false;

    private SeriesViewModel mSeriesViewModel;

    private SharedPreferences prefs;
    private int limitPreference = 0;

    boolean isInOtherOption = false;

    private RelativeLayout mRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Following Series");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new SeriesAdapter(view.getContext(), SeriesFollowingFragment.this);
        mRecyclerView.setAdapter(adapter);

        mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        requestFollowingSeries();

        mSeriesViewModel.getmAllSeries().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {

                if (results.size() == 0) {
                    mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_r_layout);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.VISIBLE);
                adapter.addSeriesPagination(results);
                offset = offset + results.size();
                progressBar.setVisibility(View.GONE);

            }
        });

        return view;
    }


    public void requestFollowingSeries() {
        mSeriesViewModel.getFollowingSeries();
    }

    @Override
    public void sendSeriesId(Integer id, SeriesDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
