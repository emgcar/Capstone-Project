package com.brave_bunny.dndhelper.create.skills_feats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brave_bunny.dndhelper.R;

public class SkillsActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.skills_container, new SkillsActivityFragment())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
