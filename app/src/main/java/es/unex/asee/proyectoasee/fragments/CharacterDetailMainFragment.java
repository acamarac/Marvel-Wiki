package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import es.unex.asee.proyectoasee.adapters.ComicsInDetailsAdapter;
import es.unex.asee.proyectoasee.adapters.ViewPagerAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Result;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Fragment which supports tabs
public class CharacterDetailMainFragment extends Fragment {

    private View view;
    private Integer id;

    private static final String TAG = "CharacterDFragment";
    private static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    private static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";

    private ApiInterface apiInterface;

    private CharacterDetails character;

    private RecyclerView rvComics;

    private ComicsInDetailsAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


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

        requestCharacterDetails();

        return view;
    }

    public void requestCharacterDetails() {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        Call<CharacterDetails> charactersCall = apiInterface.getCharacterDetails(id, ts, apiKey, hashResult);


        charactersCall.enqueue(new Callback<CharacterDetails>() {
            @Override
            public void onResponse(Call<CharacterDetails> call, Response<CharacterDetails> response) {

                if (response.code() == 401) {
                    requestCharacterDetails();
                } else {

                    character = response.body();

                    viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
                    viewPagerAdapter.addFragment(new CharacterInformationFragment(), "Information", character);
                    viewPagerAdapter.addFragment(new ComicsInDetailsFragment(), "Comics", character);
                    viewPagerAdapter.addFragment(new SeriesInDetailsFragment(), "Series", character);

                    viewPager.setAdapter(viewPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);

                }

            }

            @Override
            public void onFailure(Call<CharacterDetails> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

}
