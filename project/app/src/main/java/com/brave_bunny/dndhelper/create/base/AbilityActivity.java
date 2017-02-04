package com.brave_bunny.dndhelper.create.base;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;

import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.updateInProgressTable;

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

    public static TextView findTextView(View view, int id) {
        return (TextView) view.findViewById(id);
    }

    private void setChoiceToContentValue(ContentValues values, int id, String columnName) {

        TextView choiceText = findTextView(rootView, id);
        int choice = getScore(choiceText);
        values.put(columnName, choice);
    }

    public void setAbilityScores(View view) {
        ContentValues abilityValues = new ContentValues();

        setChoiceToContentValue(abilityValues, R.id.ability_strength, InProgressContract.CharacterEntry.COLUMN_STR);
        setChoiceToContentValue(abilityValues, R.id.ability_dexterity, InProgressContract.CharacterEntry.COLUMN_DEX);
        setChoiceToContentValue(abilityValues, R.id.ability_constitution, InProgressContract.CharacterEntry.COLUMN_CON);
        setChoiceToContentValue(abilityValues, R.id.ability_wisdom, InProgressContract.CharacterEntry.COLUMN_WIS);
        setChoiceToContentValue(abilityValues, R.id.ability_intelligence, InProgressContract.CharacterEntry.COLUMN_INT);
        setChoiceToContentValue(abilityValues, R.id.ability_charisma, InProgressContract.CharacterEntry.COLUMN_CHA);

        updateInProgressTable(this, abilityValues, index);

        this.finish();
    }
}
