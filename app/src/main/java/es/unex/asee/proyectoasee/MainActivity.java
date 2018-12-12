package es.unex.asee.proyectoasee;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.example.android.proyectoasee.R;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.characters.CharacterInformationFragment;
import es.unex.asee.proyectoasee.fragments.characters.Character_ComicsInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.characters.Character_SeriesInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.characters.CharactersFavFragment;
import es.unex.asee.proyectoasee.fragments.characters.CharactersListFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicInformationFragment;
import es.unex.asee.proyectoasee.fragments.comics.Comic_CharactersInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicsFavFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicsListFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicsReadFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicsReadingFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesFavFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesFollowingFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesInformationFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesListFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesPendingFragment;
import es.unex.asee.proyectoasee.fragments.series.SeriesSeenFragment;
import es.unex.asee.proyectoasee.fragments.series.Series_CharactersInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.series.Series_ComicsInDetailsFragment;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;
import es.unex.asee.proyectoasee.fragments.preferences.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements CharacterDetailMainFragment.CharacterDetailListener,
        ComicDetailMainFragment.ComicDetailListener,
        SeriesDetailMainFragment.SeriesDetailListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    NavController navController;

    boolean alreadyStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.fragment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_drawer_white);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setNavDrawerContent(navigationView);
        }

        setNavDrawerContent(navigationView);

        if (savedInstanceState != null) {
            alreadyStarted = savedInstanceState.getBoolean("started");
        }

        //Iniciamos el fragmento que muestra todos los personajes; solo si aún no se había
        //iniciado la aplicación
        //if (!alreadyStarted) fragmentSwitcher(1);

        //alreadyStarted = true;

        NavigationUI.setupWithNavController(navigationView, navController);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("started", alreadyStarted);
        super.onSaveInstanceState(outState);
    }

    /*@Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setNavDrawerContent(final NavigationView navigationView) {
        //NavOptions.Builder navBuilder = new NavOptions.Builder();
        //final NavOptions navOptions = navBuilder.setPopUpTo(R.id.main_layout_character, false).build();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.charactersListFragment:
                                menuItem.setChecked(true);
                                Navigation.findNavController(navigationView).navigate(R.id.charactersListFragment);
                                //Navigation.createNavigateOnClickListener(R.id.charactersListFragment);
                                //fragmentSwitcher(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.charactersFavFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(2);
                                //Navigation.createNavigateOnClickListener(R.id.charactersFavFragment, null, navOptions);
                                Navigation.findNavController(navigationView).navigate(R.id.charactersFavFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.comicsListFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(3);
                                Navigation.createNavigateOnClickListener(R.id.comicsListFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.comicsFavFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(4);
                                Navigation.createNavigateOnClickListener(R.id.comicsFavFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.comicsReadFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(5);
                                Navigation.createNavigateOnClickListener(R.id.comicsReadFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.comicsReadingFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(6);
                                Navigation.createNavigateOnClickListener(R.id.comicsReadingFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.seriesListFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(7);
                                Navigation.createNavigateOnClickListener(R.id.seriesListFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.seriesFavFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(8);
                                Navigation.createNavigateOnClickListener(R.id.seriesFavFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.seriesSeenFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(9);
                                Navigation.createNavigateOnClickListener(R.id.seriesSeenFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.seriesPendingFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(10);
                                Navigation.createNavigateOnClickListener(R.id.seriesPendingFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.seriesFollowingFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(11);
                                Navigation.createNavigateOnClickListener(R.id.seriesFollowingFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.settingsFragment:
                                menuItem.setChecked(true);
                                //fragmentSwitcher(12);
                                Navigation.createNavigateOnClickListener(R.id.settingsFragment);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    /*public void fragmentSwitcher(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 1:
                CharactersListFragment charactersFragment = new CharactersListFragment();
                fragmentTransaction.replace(R.id.fragment, charactersFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                CharactersFavFragment charactersFavFragment = new CharactersFavFragment();
                fragmentTransaction.replace(R.id.fragment, charactersFavFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                ComicsListFragment comicsFragment = new ComicsListFragment();
                fragmentTransaction.replace(R.id.fragment, comicsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                ComicsFavFragment comicsFavFragment = new ComicsFavFragment();
                fragmentTransaction.replace(R.id.fragment, comicsFavFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                ComicsReadFragment comicsReadFragment = new ComicsReadFragment();
                fragmentTransaction.replace(R.id.fragment, comicsReadFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 6:
                ComicsReadingFragment comicsReadingFragment = new ComicsReadingFragment();
                fragmentTransaction.replace(R.id.fragment, comicsReadingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 7:
                SeriesListFragment seriesFragment = new SeriesListFragment();
                fragmentTransaction.replace(R.id.fragment, seriesFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 8:
                SeriesFavFragment seriesFavFragment = new SeriesFavFragment();
                fragmentTransaction.replace(R.id.fragment, seriesFavFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 9:
                SeriesSeenFragment seriesSeenFragment = new SeriesSeenFragment();
                fragmentTransaction.replace(R.id.fragment, seriesSeenFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 10:
                SeriesPendingFragment seriesPendingFragment = new SeriesPendingFragment();
                fragmentTransaction.replace(R.id.fragment, seriesPendingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 11:
                SeriesFollowingFragment seriesFollowingFragment = new SeriesFollowingFragment();
                fragmentTransaction.replace(R.id.fragment, seriesFollowingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 12:
                SettingsFragment settings = new SettingsFragment();
                fragmentTransaction.replace(R.id.fragment, settings)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }*/

    @Override
    public void sendCharacter(CharacterDetails character, CharacterInformationFragment fragment) {
        fragment.reciveCharacter(character);
    }

    @Override
    public void sendCharacter(CharacterDetails character, Character_ComicsInDetailsFragment fragment) {
        fragment.reciveCharacter(character);
    }

    @Override
    public void sendCharacter(CharacterDetails character, Character_SeriesInDetailsFragment fragment) {
        fragment.reciveCharacter(character);
    }

    @Override
    public void sendComic(ComicDetails comic, ComicInformationFragment fragment) {
        fragment.reciveComic(comic);
    }

    @Override
    public void sendComic(ComicDetails comic, Comic_CharactersInDetailsFragment fragment) {
        fragment.reciveComic(comic);
    }

    @Override
    public void sendSeries(SeriesDetails series, SeriesInformationFragment fragment) {
        fragment.receiveSeries(series);
    }

    @Override
    public void sendSeries(SeriesDetails series, Series_ComicsInDetailsFragment fragment) {
        fragment.receiveSeries(series);
    }

    @Override
    public void sendSeries(SeriesDetails series, Series_CharactersInDetailsFragment fragment) {
        fragment.receiveSeries(series);
    }
}