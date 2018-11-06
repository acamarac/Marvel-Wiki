package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragments = new ArrayList<Fragment>();
    private final List<String> fragmentsNames = new ArrayList<String>();

    private Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragmentsNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsNames.get(position);
    }


    public void addFragment(Fragment fragment, String fragmentName) {

        fragments.add(fragment);
        fragmentsNames.add(fragmentName);
    }


    public void addFragmentComics(Fragment fragment, String fragmentName, ComicDetails comic) {

        fragments.add(fragment);
        fragmentsNames.add(fragmentName);
    }





}
