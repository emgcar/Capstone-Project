package com.brave_bunny.dndhelper.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jemma on 1/17/2017.
 */

public class CreateCharacterPagerAdapter extends FragmentPagerAdapter {

    final private int numberOfCreateCharacterFragments = 7;
    private long index;
    //boolean dataChanged = false;

    private Fragment classFragment;

    public CreateCharacterPagerAdapter(FragmentManager fm, long id) {
        super(fm);
        index = id;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CreateActivityFragment();
        Bundle args = new Bundle();
        args.putInt(CreateActivityFragment.PAGE_NUMBER, position + 1);
        args.putLong(CreateActivityFragment.ROW_INDEX, index);
        fragment.setArguments(args);

        if (position == CreateActivityFragment.PAGE_CLASS) {
            classFragment = fragment;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfCreateCharacterFragments;
    }

    public void updateClassPage(int position) {
        classFragment.create_class();
    }
}