package com.brave_bunny.dndhelper.create;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.base.AbilityActivity;
import com.brave_bunny.dndhelper.create.classes.DeityActivity;
import com.brave_bunny.dndhelper.create.classes.FamiliarActivity;
import com.brave_bunny.dndhelper.create.classes.SpellActivity;
import com.brave_bunny.dndhelper.create.classes.FeatActivity;
import com.brave_bunny.dndhelper.create.list_with_ranks.DnDRankActivity;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFinishUtil;

import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ARMOR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_WEAPON;

/**
 * Created by Jemma on 8/7/2016.
 */
public class CreateActivity extends AppCompatActivity
        implements CreateActivityFragment.OnClassSelectedListener,
        CreateActivityFragment.OnAlignSelectedListener {

    long index;
    public CreateCharacterPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.create_activity);
        getNewOrCurrentRowIndex();
        initializePagerAdapter();
        super.onCreate(savedInstanceState);
    }

    private void getNewOrCurrentRowIndex() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            index = extras.getLong(CreateActivityFragment.ROW_INDEX);
        } else {
            ContentValues blankCharacterValues = InProgressCharacterUtil.setNewInProgressContentValues();
            index = InProgressCharacterUtil.insertValuesIntoInProgressTable(this, blankCharacterValues);
        }
    }

    private void initializePagerAdapter() {
        mPagerAdapter = new CreateCharacterPagerAdapter(getSupportFragmentManager(), index);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_create);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setTag(R.string.row_adapter, mPagerAdapter);
    }

    public void onClassSelected(int position) {
        mPagerAdapter.updateClass(position);
        mPagerAdapter.notifyDataSetChanged();
    }

    public void onAlignSelected(int position) {
        mPagerAdapter.updateAlign(position);
        mPagerAdapter.notifyDataSetChanged();
    }

    public void createCharacter(View view) {
        int characterState = InProgressFinishUtil.checkStateOfCharacterChoices(this, index);

        if (characterState == InProgressFinishUtil.STATE_COMPLETE) {
            InProgressFinishUtil.createNewCharacter(this, index);
            InProgressFinishUtil.removeAllInProgressCharacterData(this, index);

            this.finish();
        }
    }

    public void launchAbilityScoreSelector(View view) {

        Intent abilityActivity = new Intent(this, AbilityActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        abilityActivity.putExtra(AbilityActivity.inprogressValues, values);
        abilityActivity.putExtra(AbilityActivity.indexValue, index);

        startActivity(abilityActivity);
    }

    public void launchDeitySelector(View view) {
        Intent deityActivity = new Intent(this, DeityActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        deityActivity.putExtra(DeityActivity.inprogressValues, values);
        deityActivity.putExtra(DeityActivity.indexValue, index);

        startActivity(deityActivity);
    }

    public void launchSpellSelector(View view) {
        Intent spellActivity = new Intent(this, SpellActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        spellActivity.putExtra(SpellActivity.inprogressValues, values);
        spellActivity.putExtra(SpellActivity.indexValue, index);

        startActivity(spellActivity);

    }

    public void launchFamiliarSelector(View view) {
        Intent familiarActivity = new Intent(this, FamiliarActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        familiarActivity.putExtra(FamiliarActivity.inprogressValues, values);
        familiarActivity.putExtra(FamiliarActivity.indexValue, index);

        startActivity(familiarActivity);
    }

    public void launchSkillSelector(View view) {
        Intent activity = new Intent(this, DnDRankActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        activity.putExtra(DnDRankActivity.inprogressValues, values);
        activity.putExtra(DnDRankActivity.indexValue, index);
        activity.putExtra(DnDRankActivity.listType, TYPE_SKILL);

        startActivity(activity);
    }

    public void launchFeatSelector(View view) {
        Intent featActivity = new Intent(this, FeatActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        featActivity.putExtra(FeatActivity.inprogressValues, values);
        featActivity.putExtra(FeatActivity.indexValue, index);

        startActivity(featActivity);
    }

    public void launchArmorSelector(View view) {
        Intent activity = new Intent(this, DnDRankActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        activity.putExtra(DnDRankActivity.inprogressValues, values);
        activity.putExtra(DnDRankActivity.indexValue, index);
        activity.putExtra(DnDRankActivity.listType, TYPE_ARMOR);

        startActivity(activity);
    }

    public void launchWeaponSelector(View view) {
        Intent activity = new Intent(this, DnDRankActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        activity.putExtra(DnDRankActivity.inprogressValues, values);
        activity.putExtra(DnDRankActivity.indexValue, index);
        activity.putExtra(DnDRankActivity.listType, TYPE_WEAPON);

        startActivity(activity);
    }

    public void launchItemSelector(View view) {
        Intent activity = new Intent(this, DnDRankActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        activity.putExtra(DnDRankActivity.inprogressValues, values);
        activity.putExtra(DnDRankActivity.indexValue, index);
        activity.putExtra(DnDRankActivity.listType, TYPE_ITEM);

        startActivity(activity);
    }
}
