package es.unex.asee.proyectoasee.fragments.series;

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
import es.unex.asee.proyectoasee.database.ViewModel.SeriesViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Series;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;

public class SeriesDetailMainFragment extends Fragment {

    private View view;
    private Integer id;

    private SeriesDetails series;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    private SeriesDetailListener mCallback;
    private SeriesViewModel mSeriesViewModel;


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

        mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
        }

        Bundle bundle = getArguments();
        if (bundle != null)
            id = bundle.getInt("id");

        setHasOptionsMenu(false);

        requestSeriesDetails();

        return view;
    }

    public void requestSeriesDetails() {

        series = mSeriesViewModel.getSeriesById(id);

        generateTabs();

    }

    public void generateTabs() {

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        SeriesInformationFragment seriesInformationFragment = new SeriesInformationFragment();
        Series_ComicsInDetailsFragment series_comicsInDetailsFragment = new Series_ComicsInDetailsFragment();
        Series_CharactersInDetailsFragment series_charactersInDetailsFragment = new Series_CharactersInDetailsFragment();

        viewPagerAdapter.addFragment(seriesInformationFragment, "Information");
        viewPagerAdapter.addFragment(series_comicsInDetailsFragment, "Comics");
        viewPagerAdapter.addFragment(series_charactersInDetailsFragment, "Characters");

        mCallback.sendSeries(series, seriesInformationFragment);
        mCallback.sendSeries(series, series_comicsInDetailsFragment);
        mCallback.sendSeries(series, series_charactersInDetailsFragment);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    //Method to communicate fragments
    public interface SeriesDetailListener {
        void sendSeries(SeriesDetails series, SeriesInformationFragment fragment);
        void sendSeries(SeriesDetails series, Series_ComicsInDetailsFragment fragment);
        void sendSeries(SeriesDetails series, Series_CharactersInDetailsFragment fragment);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SeriesDetailMainFragment.SeriesDetailListener) {
            mCallback = (SeriesDetailMainFragment.SeriesDetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SeriesDetailListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}
