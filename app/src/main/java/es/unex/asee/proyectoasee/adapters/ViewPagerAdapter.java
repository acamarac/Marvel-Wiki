package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;

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

    public void addFragment(Fragment fragment, String fragmentName, CharacterDetails character) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("Character", character);
        fragment.setArguments(bundle);

        fragments.add(fragment);
        fragmentsNames.add(fragmentName);
    }


}
