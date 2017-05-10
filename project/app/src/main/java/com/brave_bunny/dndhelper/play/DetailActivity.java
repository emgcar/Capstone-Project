package com.brave_bunny.dndhelper.play;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.CreateActivityFragment;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.play.battle.CastSpellActivity;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_DWARF;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_ELF;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_HUMAN;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_DOMAIN;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FEAT;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;
import static com.brave_bunny.dndhelper.play.battle.CastSpellActivityFragment.ROW_INDEX;

public class DetailActivity extends AppCompatActivity {

    private long index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            index = extras.getLong(CreateActivityFragment.ROW_INDEX);
            DetailActivityFragment detailFragment = new DetailActivityFragment();
            detailFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }
    }

    public void showSpells(View view) {
        Intent spellActivity = new Intent(this, CastSpellActivity.class);
        spellActivity.putExtra(CastSpellActivity.indexValue, index);
        spellActivity.putExtra(CastSpellActivity.listType, TYPE_SPELL);
        startActivity(spellActivity);
    }

    public void showSkills(View view) {
        Intent activity = new Intent(this, CastSpellActivity.class);
        activity.putExtra(CastSpellActivity.indexValue, index);
        activity.putExtra(CastSpellActivity.listType, TYPE_SKILL);
        startActivity(activity);
    }


    public static class DetailActivityFragment extends Fragment {

        ViewHolder mViewHolder;
        View mRootView;

        private long index;
        private ContentValues values;

        public DetailActivityFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle args = getArguments();
            index = args.getLong(ROW_INDEX);

            mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
            mViewHolder = new ViewHolder(mRootView);

            values = CharacterUtil.getCharacterRow(getContext(), index);
            setInformation();

            return mRootView;
        }

        private void setInformation() {
            mViewHolder.mNameView.setText(CharacterUtil.getCharacterName(values));

            int level = CharacterUtil.getCharacterLevel(values);
            String levelText = getContext().getString(R.string.total_level, level);
            mViewHolder.mLevelView.setText(levelText);
            mViewHolder.mLevelView.setContentDescription(levelText);

            int exp = CharacterUtil.getCharacterExperience(values);
            String expText = getContext().getString(R.string.experience, exp);
            mViewHolder.mExperienceView.setText(expText);
            mViewHolder.mExperienceView.setContentDescription(expText);

            float money = CharacterUtil.getCharacterMoney(values);
            String moneyText = getContext().getString(R.string.money, money);
            mViewHolder.mMoneyView.setText(moneyText);
            mViewHolder.mMoneyView.setContentDescription(moneyText);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView;
        public TextView mLevelView;
        public TextView mExperienceView;
        public TextView mMoneyView;


        public ViewHolder(View view) {
            super(view);

            mNameView = (TextView) view.findViewById(R.id.name_text);
            mLevelView = (TextView) view.findViewById(R.id.level_text);
            mExperienceView = (TextView) view.findViewById(R.id.experience_text);
            mMoneyView = (TextView) view.findViewById(R.id.money_text);
        }
    }
}
