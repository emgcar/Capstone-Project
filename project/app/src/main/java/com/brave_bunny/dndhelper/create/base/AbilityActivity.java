package com.brave_bunny.dndhelper.create.base;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtil.updateInProgressTable;

public class AbilityActivity extends AppCompatActivity {

    public static final String indexValue = "ROW_INDEX";
    public static final String inprogressValues = "INPROGRESS_TABLE_VALUES";

    private long index;

    private View rootView;
    private AbilityViewHolder abilityViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability);
        rootView = this.findViewById(android.R.id.content);
        abilityViewHolder = new AbilityViewHolder(rootView);

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

    public void setAbilityScores(View view) {
        ContentValues abilityValues = new ContentValues();

        int strScore = getScore(abilityViewHolder.mStrText);
        int dexScore = getScore(abilityViewHolder.mDexText);
        int conScore = getScore(abilityViewHolder.mConText);
        int wisScore = getScore(abilityViewHolder.mWisText);
        int intScore = getScore(abilityViewHolder.mIntText);
        int chaScore = getScore(abilityViewHolder.mChaText);

        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_STR, strScore);
        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_DEX, dexScore);
        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_CON, conScore);
        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_WIS, wisScore);
        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_INT, intScore);
        abilityValues.put(InProgressContract.CharacterEntry.COLUMN_CHA, chaScore);

        updateInProgressTable(this, InProgressContract.CharacterEntry.TABLE_NAME,
                abilityValues, index);

        this.finish();
    }

    public static class AbilityViewHolder extends RecyclerView.ViewHolder {
        public TextView mStrText;
        public TextView mDexText;
        public TextView mConText;
        public TextView mWisText;
        public TextView mIntText;
        public TextView mChaText;

        public AbilityViewHolder(View view) {
            super(view);

            mStrText = (TextView) view.findViewById(R.id.ability_strength);
            mDexText = (TextView) view.findViewById(R.id.ability_dexterity);
            mConText = (TextView) view.findViewById(R.id.ability_constitution);
            mWisText = (TextView) view.findViewById(R.id.ability_wisdom);
            mIntText = (TextView) view.findViewById(R.id.ability_intelligence);
            mChaText = (TextView) view.findViewById(R.id.ability_charisma);
        }
    }
}
