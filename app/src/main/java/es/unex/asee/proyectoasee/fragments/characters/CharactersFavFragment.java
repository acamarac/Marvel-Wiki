package es.unex.asee.proyectoasee.fragments.characters;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import es.unex.asee.proyectoasee.adapters.characters.CharactersAdapter;
import es.unex.asee.proyectoasee.database.ViewModel.CharacterViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;

public class CharactersFavFragment extends Fragment implements CharactersAdapter.CharactersAdapterListener {


    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private CharactersAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private int offset = 0;

    private CharacterViewModel mCharacterViewModel;

    private RelativeLayout mRelativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Favorite Characters");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new CharactersAdapter(view.getContext(), CharactersFavFragment.this);
        mRecyclerView.setAdapter(adapter);

        mCharacterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);

        requestFavCharacters();

        mCharacterViewModel.getmAllCharacters().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {

                if (results.size() == 0) {
                    mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_r_layout);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.VISIBLE);
                adapter.addCharactersPagination(results);
                offset = offset + results.size();
                progressBar.setVisibility(View.GONE);

            }
        });

        return view;
    }

    public void requestFavCharacters() {
        mCharacterViewModel.getAllFavoriteCharacters();
    }


    @Override
    public void sendCharacterId(Integer id, CharacterDetailMainFragment fragment) {
        fragment.receiveCharacterId(id);
    }
}
