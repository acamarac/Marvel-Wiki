package es.unex.asee.proyectoasee.fragments.comics;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.adapters.ViewPagerAdapter;
import es.unex.asee.proyectoasee.database.ViewModel.ComicViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;

public class ComicDetailMainFragment extends Fragment {

    private View view;
    private Integer id;

    private ComicDetails comic;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    private ComicDetailListener mCallback;
    private ComicViewModel mComicViewModel;


    public void receiveId(Integer id) {
        this.id = id;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("id", id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_detail_main_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mComicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
        }

        Bundle bundle = getArguments();
        if (bundle != null)
            id = bundle.getInt("id");

        setHasOptionsMenu(false);

        requestComicDetails();

        return view;
    }

    public void requestComicDetails() {

        comic = mComicViewModel.getComicById(id);

        generateTabs();

    }

    public void generateTabs() {

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        ComicInformationFragment comicInformationFragment = new ComicInformationFragment();
        Comic_CharactersInDetailsFragment comic_charactersInDetailsFragment = new Comic_CharactersInDetailsFragment();

        viewPagerAdapter.addFragment(comicInformationFragment, "Information");
        viewPagerAdapter.addFragment(comic_charactersInDetailsFragment, "Character");

        mCallback.sendComic(comic, comicInformationFragment);
        mCallback.sendComic(comic, comic_charactersInDetailsFragment);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    //Method to communicate fragments
    public interface ComicDetailListener {
        void sendComic(ComicDetails comic, ComicInformationFragment fragment);
        void sendComic(ComicDetails comic, Comic_CharactersInDetailsFragment fragment);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComicDetailMainFragment.ComicDetailListener) {
            mCallback = (ComicDetailMainFragment.ComicDetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ComicDetailListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}
