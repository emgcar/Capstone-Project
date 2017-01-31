package com.brave_bunny.dndhelper.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jemma on 1/17/2017.
 */

public class CreateCharacterPagerAdapter extends FragmentPagerAdapter {

    final private int numberOfCreateCharacterFragments = 5;
    private long index;

    private CreateActivityFragment classFragment;
    private CreateActivityFragment skillFragment;

    public CreateCharacterPagerAdapter(FragmentManager fm, long id) {
        super(fm);
        index = id;
    }

    @Override
    public Fragment getItem(int position) {
        CreateActivityFragment fragment = new CreateActivityFragment();
        Bundle args = new Bundle();
        args.putInt(CreateActivityFragment.PAGE_NUMBER, position + 1);
        args.putLong(CreateActivityFragment.ROW_INDEX, index);
        fragment.setArguments(args);

        if ((position + 1) == CreateActivityFragment.PAGE_CLASS) {
            classFragment = fragment;
        } else if ((position + 1) == CreateActivityFragment.PAGE_SKILL) {
            skillFragment = fragment;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfCreateCharacterFragments;
    }

    public void updateClass(int position) {
        if (classFragment != null) {
            classFragment.create_class(position);
        }
        //TODO don't let skills button be enabled when class is not chosen
        if( skillFragment != null) {
            skillFragment.updateSkillPage(position);
        }
    }

    public void updateAlign(int position) {
        if (classFragment != null) {
            classFragment.update_align(position);
        }
    }
}
