package es.unex.asee.proyectoasee.fragments.series;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class SeriesPendingFragment extends Fragment implements SeriesAdapter.SeriesAdapterListener {

    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SeriesAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;

    private SeriesViewModel mSeriesViewModel;

    private RelativeLayout mRelativeLayout;

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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pending Series");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new SeriesAdapter(view.getContext(), SeriesPendingFragment.this);
        mRecyclerView.setAdapter(adapter);

        requestPendingSeries();

        return view;
    }


    private void requestPendingSeries() {
        mSeriesViewModel.getPendingSeries();
    }

    private void setObserver() {

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

    }

    @Override
    public void sendSeriesId(Integer id, SeriesDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
