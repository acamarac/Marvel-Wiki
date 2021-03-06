package es.unex.asee.proyectoasee.fragments.comics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import es.unex.asee.proyectoasee.adapters.comics.ComicsAdapter;
import es.unex.asee.proyectoasee.database.ViewModel.ComicViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;

public class ComicsFavFragment extends Fragment implements ComicsAdapter.ComicsAdapterListener {

    private View view;

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private ComicsAdapter adapter;

    private GridLayoutManager mGridLayoutManager;

    private ComicViewModel mComicViewModel;

    private RelativeLayout mRelativeLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mComicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);

        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite Comics");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Create GridView
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        setHasOptionsMenu(true);

        adapter = new ComicsAdapter(view.getContext(), ComicsFavFragment.this);
        mRecyclerView.setAdapter(adapter);

        requestFavComics();

        return view;
    }


    private void requestFavComics() {
        mComicViewModel.getFavoriteComics();
    }

    private void setObserver() {

        mComicViewModel.getmAllComics().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {

                if (results.size() == 0) {
                    mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_data_r_layout);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.VISIBLE);
                adapter.addComicsPagination(results);
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void sendComicId(Integer id, ComicDetailMainFragment fragment) {
        fragment.receiveId(id);
    }
}
