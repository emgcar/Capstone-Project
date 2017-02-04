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
import com.brave_bunny.dndhelper.create.inventory.ArmorActivity;
import com.brave_bunny.dndhelper.create.inventory.ItemActivity;
import com.brave_bunny.dndhelper.create.inventory.WeaponActivity;
import com.brave_bunny.dndhelper.create.skills_feats.FeatActivity;
import com.brave_bunny.dndhelper.create.skills_feats.SkillsActivity;
import com.brave_bunny.dndhelper.database.CharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

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
        int characterState = InProgressCharacterUtil.checkStateOfCharacterChoices(this, index);

        if (characterState == InProgressCharacterUtil.STATE_COMPLETE) {
            CharacterUtil.createNewCharacter(this, index);
            InProgressCharacterUtil.removeAllCharacterData(this, index);

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
        Intent skillsActivity = new Intent(this, SkillsActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        skillsActivity.putExtra(SkillsActivity.inprogressValues, values);
        skillsActivity.putExtra(SkillsActivity.indexValue, index);

        startActivity(skillsActivity);
    }

    public void launchFeatSelector(View view) {
        Intent featActivity = new Intent(this, FeatActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        featActivity.putExtra(FeatActivity.inprogressValues, values);
        featActivity.putExtra(FeatActivity.indexValue, index);

        startActivity(featActivity);
    }

    public void launchArmorSelector(View view) {
        Intent armorActivity = new Intent(this, ArmorActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        armorActivity.putExtra(ArmorActivity.inprogressValues, values);
        armorActivity.putExtra(ArmorActivity.indexValue, index);

        startActivity(armorActivity);
    }

    public void launchWeaponSelector(View view) {
        Intent weaponActivity = new Intent(this, WeaponActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        weaponActivity.putExtra(WeaponActivity.inprogressValues, values);
        weaponActivity.putExtra(WeaponActivity.indexValue, index);

        startActivity(weaponActivity);
    }

    public void launchItemSelector(View view) {
        Intent itemActivity = new Intent(this, ItemActivity.class);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(this, index);
        itemActivity.putExtra(ItemActivity.inprogressValues, values);
        itemActivity.putExtra(ItemActivity.indexValue, index);

        startActivity(itemActivity);
    }
}
