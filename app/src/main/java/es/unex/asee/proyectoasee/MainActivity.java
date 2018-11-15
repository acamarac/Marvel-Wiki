package es.unex.asee.proyectoasee;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.android.proyectoasee.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import es.unex.asee.proyectoasee.preferences.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements CharacterDetailMainFragment.CharacterDetailListener,
        ComicDetailMainFragment.ComicDetailListener,
        SeriesDetailMainFragment.SeriesDetailListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    boolean alreadyStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if (!alreadyStarted) fragmentSwitcher(1);

        alreadyStarted = true;


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("started", alreadyStarted);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

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
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_all_characters:
                                menuItem.setChecked(true);
                                fragmentSwitcher(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_fav_characters:
                                menuItem.setChecked(true);
                                fragmentSwitcher(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_all_comics:
                                menuItem.setChecked(true);
                                fragmentSwitcher(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_fav_comics:
                                menuItem.setChecked(true);
                                fragmentSwitcher(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_read_comics:
                                menuItem.setChecked(true);
                                fragmentSwitcher(5);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_reading_comics:
                                menuItem.setChecked(true);
                                fragmentSwitcher(6);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_all_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(7);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_fav_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(8);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_seen_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(9);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_pending_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(10);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_following_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(11);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_preferences:
                                menuItem.setChecked(true);
                                fragmentSwitcher(12);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void fragmentSwitcher(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
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
    }

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