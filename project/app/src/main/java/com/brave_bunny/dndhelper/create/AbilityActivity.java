package com.brave_bunny.dndhelper.create;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.CharacterContract;

public class AbilityActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";

    private long index;

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability);
        rootView = this.findViewById(android.R.id.content);

        Bundle extras = getIntent().getExtras();
        index = extras.getLong(indexValue);

        if (savedInstanceState == null) {
            AbilityFragment abilityFragment = new AbilityFragment();
            abilityFragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ability_container, abilityFragment)
                    .commit();
        }
    }

    int getScore(TextView view) {
        return Integer.parseInt(view.getText().toString());
    }

    private void setChoiceToContentValue(ContentValues values, int id, String columnName) {
        TextView choiceText = Utility.findTextView(rootView, id);
        int choice = getScore(choiceText);
        Utility.putInContentValue(values, columnName, choice);
    }

    public void setAbilityScores(View view) {
        ContentValues abilityValues = new ContentValues();

        setChoiceToContentValue(abilityValues, R.id.ability_strength,
                CharacterContract.InProgressEntry.COLUMN_STR);

        setChoiceToContentValue(abilityValues, R.id.ability_dexterity,
                CharacterContract.InProgressEntry.COLUMN_DEX);

        setChoiceToContentValue(abilityValues, R.id.ability_constitution,
                CharacterContract.InProgressEntry.COLUMN_CON);

        setChoiceToContentValue(abilityValues, R.id.ability_intelligence,
                CharacterContract.InProgressEntry.COLUMN_INT);

        setChoiceToContentValue(abilityValues, R.id.ability_wisdom,
                CharacterContract.InProgressEntry.COLUMN_WIS);

        setChoiceToContentValue(abilityValues, R.id.ability_charisma,
                CharacterContract.InProgressEntry.COLUMN_CHA);

        Utility.updateValuesInTable(this, CharacterContract.InProgressEntry.TABLE_NAME,
                abilityValues, index);

        this.finish();
    }
}
