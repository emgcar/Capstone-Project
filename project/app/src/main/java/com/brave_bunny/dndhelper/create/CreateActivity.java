package com.brave_bunny.dndhelper.create;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.base.AbilityActivity;
import com.brave_bunny.dndhelper.create.classes.DnDListActivity;
import com.brave_bunny.dndhelper.create.list_with_ranks.DnDRankActivity;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFinishUtil;

import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ARMOR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_DOMAIN;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FAMILIAR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FEAT;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;
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
        Intent spellActivity = new Intent(this, DnDListActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        spellActivity.putExtra(DnDListActivity.inprogressValues, values);
        spellActivity.putExtra(DnDListActivity.indexValue, index);
        spellActivity.putExtra(DnDListActivity.listType, TYPE_DOMAIN);

        startActivity(spellActivity);
    }

    public void launchSpellSelector(View view) {
        Intent spellActivity = new Intent(this, DnDListActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        spellActivity.putExtra(DnDListActivity.inprogressValues, values);
        spellActivity.putExtra(DnDListActivity.indexValue, index);
        spellActivity.putExtra(DnDListActivity.listType, TYPE_SPELL);

        startActivity(spellActivity);
    }

    public void launchFamiliarSelector(View view) {
        Intent spellActivity = new Intent(this, DnDListActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        spellActivity.putExtra(DnDListActivity.inprogressValues, values);
        spellActivity.putExtra(DnDListActivity.indexValue, index);
        spellActivity.putExtra(DnDListActivity.listType, TYPE_FAMILIAR);

        startActivity(spellActivity);
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
        Intent spellActivity = new Intent(this, DnDListActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        spellActivity.putExtra(DnDListActivity.inprogressValues, values);
        spellActivity.putExtra(DnDListActivity.indexValue, index);
        spellActivity.putExtra(DnDListActivity.listType, TYPE_FEAT);

        startActivity(spellActivity);
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
