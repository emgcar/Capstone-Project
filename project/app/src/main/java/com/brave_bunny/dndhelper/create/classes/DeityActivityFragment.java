package com.brave_bunny.dndhelper.create.classes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brave_bunny.dndhelper.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeityActivityFragment extends Fragment {

    public DeityActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deity, container, false);
    }
}
