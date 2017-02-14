package com.brave_bunny.dndhelper.create.list_with_ranks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brave_bunny.dndhelper.R;

public class DnDRankActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";
    public static final String listType = "LIST_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.skills_container, new DndRankActivityFragment())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
