package es.unex.asee.proyectoasee;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.characters.CharacterInformationFragment;
import es.unex.asee.proyectoasee.fragments.characters.Character_ComicsInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.characters.Character_SeriesInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.characters.CharactersListFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicInformationFragment;
import es.unex.asee.proyectoasee.fragments.comics.Comic_CharactersInDetailsFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicsListFragment;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;

public class MainActivity extends AppCompatActivity
        implements CharacterDetailMainFragment.CharacterDetailListener,
        ComicDetailMainFragment.ComicDetailListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

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

        //Iniciamos el fragmento Resume
        //TODO cambiar por position 0
        //fragmentSwitcher(1);

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

    private void setNavDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_characters:
                                menuItem.setChecked(true);
                                fragmentSwitcher(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_comics:
                                menuItem.setChecked(true);
                                fragmentSwitcher(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_series:
                                menuItem.setChecked(true);
                                fragmentSwitcher(3);
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
            /*case 0:
                ResumeFragment resumeFragment = new ResumeFragment();
                fragmentTransaction.replace(R.id.fragment, resumeFragment);
                fragmentTransaction.commit();
                break;*/
            case 1:
                CharactersListFragment charactersFragment = new CharactersListFragment();
                fragmentTransaction.replace(R.id.fragment, charactersFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                ComicsListFragment comicsFragment = new ComicsListFragment();
                fragmentTransaction.replace(R.id.fragment, comicsFragment);
                fragmentTransaction.commit();
                break;
            /*case 3:
                SeriesListFragment seriesFragment = new SeriesListFragment();
                fragmentTransaction.replace(R.id.fragment, seriesFragment);
                fragmentTransaction.commit();
                break;*/
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
}