package es.unex.asee.proyectoasee.fragments.series;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.adapters.series.Series_ComicsInDetailsAdapter;
import es.unex.asee.proyectoasee.fragments.comics.ComicDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;


public class Series_ComicsInDetailsFragment extends Fragment implements Series_ComicsInDetailsAdapter.Series_ComicsInDetailsAdapterListener{

    View view;


    private static final String TAG = "CharacterComicsFragment";

    RecyclerView rvComics;
    LinearLayoutManager mLinearLayoutManager;
    Series_ComicsInDetailsAdapter adapter;
    SeriesDetails series;

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void receiveSeries(SeriesDetails series) {
        this.series = series;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("series", series);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.comics_in_details_fragment, container, false);

        rvComics = (RecyclerView) view.findViewById(R.id.rViewComics);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvComics.setLayoutManager(mLinearLayoutManager);

        if (savedInstanceState != null) {
            series = (SeriesDetails) savedInstanceState.getSerializable("series");
        }

        if (series.getData().getResults().get(0).getComics().getItems().size() == 0) {
            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
            mRelativeLayout.setVisibility(View.VISIBLE);
        }

        adapter = new Series_ComicsInDetailsAdapter(series.getData().getResults().get(0).getComics().getItems(), view.getContext(), Series_ComicsInDetailsFragment.this);
        rvComics.setAdapter(adapter);

        return view;

    }

    @Override
    public void sendId(Integer id, ComicDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
