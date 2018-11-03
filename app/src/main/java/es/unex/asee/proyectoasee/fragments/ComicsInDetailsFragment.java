package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.adapters.ComicsInDetailsAdapter;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;

public class ComicsInDetailsFragment extends Fragment {

    View view;


    private static final String TAG = "CharacterComicsFragment";

    RecyclerView rvComics;
    LinearLayoutManager mLinearLayoutManager;
    ComicsInDetailsAdapter adapter;
    CharacterDetails character;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("Character")) {
            character = getArguments().getParcelable("Character");

            Log.d(TAG, "onCreate: id " + character.getData().getResults().get(0).getName());

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.comics_in_details_fragment, container, false);

        rvComics = (RecyclerView) view.findViewById(R.id.rViewComics);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvComics.setLayoutManager(mLinearLayoutManager);

        adapter = new ComicsInDetailsAdapter(character.getData().getResults().get(0).getComics().getItems(), view.getContext());
        rvComics.setAdapter(adapter);

        return view;

    }


}
