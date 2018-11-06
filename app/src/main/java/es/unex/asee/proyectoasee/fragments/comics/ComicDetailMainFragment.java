package es.unex.asee.proyectoasee.fragments.comics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.proyectoasee.R;

import java.util.Date;

import es.unex.asee.proyectoasee.adapters.ViewPagerAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailMainFragment extends Fragment {

    private View view;
    private Integer id;

    private static final String TAG = "CharacterDFragment";
    private static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    private static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";

    private ApiInterface apiInterface;

    private ComicDetails comic;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    private ComicDetailListener mCallback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("id")) {
            id = getArguments().getInt("id");

            Log.d(TAG, "onCreate: id " + id);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_detail_main_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        setHasOptionsMenu(false);

        requestComicDetails();

        return view;
    }

    public void requestComicDetails() {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        Call<ComicDetails> comicCall = apiInterface.getComicDetails(id, ts, apiKey, hashResult);


        comicCall.enqueue(new Callback<ComicDetails>() {
            @Override
            public void onResponse(Call<ComicDetails> call, Response<ComicDetails> response) {

                if (response.code() == 401) {
                    requestComicDetails();
                } else {

                    comic = response.body();

                    generateTabs();

                }

            }

            @Override
            public void onFailure(Call<ComicDetails> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    public void generateTabs() {

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        ComicInformationFragment comicInformationFragment = new ComicInformationFragment();
        Comic_CharactersInDetailsFragment comic_charactersInDetailsFragment = new Comic_CharactersInDetailsFragment();

        viewPagerAdapter.addFragmentComics(comicInformationFragment, "Information", comic);
        viewPagerAdapter.addFragmentComics(comic_charactersInDetailsFragment, "CharacterEntity", comic);
        //viewPagerAdapter.addFragmentComics(new Comic_SeriesInDetailsFragment(), "Series", comic);

        mCallback.sendComic(comic, comicInformationFragment);
        mCallback.sendComic(comic, comic_charactersInDetailsFragment);
        //mCallback.sendComic(comic, seriesInDetailsFragment);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    //Method to communicate fragments
    public interface ComicDetailListener {
        void sendComic(ComicDetails comic, ComicInformationFragment fragment);
        void sendComic(ComicDetails comic, Comic_CharactersInDetailsFragment fragment);
        //void sendComic(ComicDetails comic, Comic_SeriesInDetailsFragment fragment);
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
