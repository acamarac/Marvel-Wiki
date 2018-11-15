package es.unex.asee.proyectoasee.fragments.comics;

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

import es.unex.asee.proyectoasee.adapters.comics.Comic_CharactersInDetailsAdapter;
import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;

public class Comic_CharactersInDetailsFragment extends Fragment implements Comic_CharactersInDetailsAdapter.Comic_CharactersInDetailsAdapterListener{

    View view;


    private static final String TAG = "ComicChFragment";

    RecyclerView rvCharacters;
    LinearLayoutManager mLinearLayoutManager;
    Comic_CharactersInDetailsAdapter adapter;
    ComicDetails comic;

    RelativeLayout mRelativeLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void reciveComic(ComicDetails comic) {
        this.comic = comic;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("comic", comic);
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
            comic = (ComicDetails) savedInstanceState.getSerializable("comic");
        }

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_layout);

        if (comic.getData().getResults().get(0).getCharacters().getItems().size() == 0) {
            mRelativeLayout.setVisibility(View.VISIBLE);
        }

        adapter = new Comic_CharactersInDetailsAdapter(comic.getData().getResults().get(0).getCharacters().getItems(), view.getContext(), Comic_CharactersInDetailsFragment.this);
        rvCharacters.setAdapter(adapter);

        return view;

    }

    @Override
    public void sendId(Integer id, CharacterDetailMainFragment fragment) {
        fragment.receiveCharacterId(id);
    }
}
