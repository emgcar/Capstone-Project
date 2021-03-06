package com.brave_bunny.dndhelper.create.abilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

public class AbilityActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";

    private long index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability);

        Bundle extras = getIntent().getExtras();
        index = extras.getLong(indexValue);

        if (savedInstanceState == null) {
            AbilityFragment abilityFragment = new AbilityFragment();
            abilityFragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, abilityFragment)
                    .commit();
        }
    }

    public void setAbilityScores(View view) {
        this.finish();
    }

    public void rerollScores(View view) {
        InProgressCharacterUtil.setNewAbilityChoices(getBaseContext(), index);
        onRerollSelected();
    }

    public void onRerollSelected() {

        AbilityFragment abilityFragment = (AbilityFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);

        if (abilityFragment != null) {
            abilityFragment.updateTexts();
        }
    }
}
