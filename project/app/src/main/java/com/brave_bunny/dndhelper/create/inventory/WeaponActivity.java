package com.brave_bunny.dndhelper.create.inventory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.brave_bunny.dndhelper.R;

public class WeaponActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WeaponActivityFragment())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
