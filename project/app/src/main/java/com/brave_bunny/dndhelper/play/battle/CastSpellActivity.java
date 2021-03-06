package com.brave_bunny.dndhelper.play.battle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.brave_bunny.dndhelper.R;

public class CastSpellActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String characterValues = "CHARACTER_VALUES";
    public static final String listType = "LIST_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
