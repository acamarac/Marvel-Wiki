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

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.adapters.series.Series_CharactersInDetailsAdapter;
import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;


public class Series_CharactersInDetailsFragment extends Fragment implements Series_CharactersInDetailsAdapter.Series_CharactersInDetailsAdapterListener{

    View view;

    RecyclerView rvCharacters;
    LinearLayoutManager mLinearLayoutManager;
    Series_CharactersInDetailsAdapter adapter;
    SeriesDetails series;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void receiveSeries(SeriesDetails comic) {
        this.series = comic;
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


        rvCharacters = (RecyclerView) view.findViewById(R.id.rViewComics);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvCharacters.setLayoutManager(mLinearLayoutManager);

        if (savedInstanceState != null) {
            series = (SeriesDetails) savedInstanceState.getSerializable("series");
        }

        adapter = new Series_CharactersInDetailsAdapter(series.getData().getResults().get(0).getCharacters().getItems(), view.getContext(), Series_CharactersInDetailsFragment.this);
        rvCharacters.setAdapter(adapter);

        return view;

    }

    @Override
    public void sendId(Integer id, CharacterDetailMainFragment fragment) {
        fragment.receiveCharacterId(id);
    }
}
