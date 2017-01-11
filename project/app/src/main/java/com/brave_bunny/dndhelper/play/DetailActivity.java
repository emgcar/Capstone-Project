package com.brave_bunny.dndhelper.play;

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

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            DetailActivityFragment detailFragment = new DetailActivityFragment();
            detailFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }
    }



    public static class DetailActivityFragment extends Fragment {

        ViewHolder mViewHolder;
        View mRootView;

        private long index;

        private String name;
        private int gender;
        private int race;
        private String age;
        private String weight;
        private String height;
        private String religion;
        private int alignment;

        private String strength;
        private String dexterity;
        private String constitution;
        private String intelligence;
        private String wisdom;
        private String charisma;

        private String base_attack;
        private String fortitude;
        private String reflex;
        private String will;

        private String money;
        private String light_load;
        private String medium_load;
        private String heavy_load;

        private String ac;
        private String hp_max;
        private String hp_curr;

        public DetailActivityFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle args = getArguments();
            index = args.getLong(CreateActivity.CreateActivityFragment.ROW_INDEX);

            mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
            mViewHolder = new ViewHolder(mRootView);

            getInformationFromDatabase();
            setInformation();

            return mRootView;
        }

        private void getInformationFromDatabase() {
            CharacterDbHelper dbHelper = new CharacterDbHelper(getContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                        + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});
                try {
                    int nameIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_NAME);
                    int genderIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_GENDER);
                    int raceIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_RACE_ID);
                    int ageIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_AGE);
                    int weightIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_WEIGHT);
                    int heightIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_HEIGHT);
                    int religionIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_RELIGION_ID);
                    int alignIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_ALIGN);

                    int strIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_STR);
                    int dexIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_DEX);
                    int conIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_CON);
                    int intIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_INT);
                    int wisIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_WIS);
                    int chaIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_CHA);

                    int attackIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK);
                    int fortIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_FORT);
                    int refIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_REF);
                    int willIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_WILL);

                    int moneyIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_MONEY);
                    int lightLoadIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD);
                    int medLoadIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_MED_LOAD);
                    int heavyLoadIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD);

                    int acIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_AC);
                    int hpMaxIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_HP_MAX);
                    int hpCurrIndex = cursor.getColumnIndex(CharacterContract.CharacterEntry.COLUMN_HP_CURR);

                    cursor.moveToFirst();

                    name = cursor.getString(nameIndex);
                    gender = cursor.getInt(genderIndex);
                    race = cursor.getInt(raceIndex);
                    age = Integer.toString(cursor.getInt(ageIndex));
                    weight = Integer.toString(cursor.getInt(weightIndex));
                    height = Float.toString(cursor.getFloat(heightIndex));
                    religion = Integer.toString(cursor.getInt(religionIndex));
                    alignment = cursor.getInt(alignIndex);

                    strength = Integer.toString(cursor.getInt(strIndex));
                    dexterity = Integer.toString(cursor.getInt(dexIndex));
                    constitution = Integer.toString(cursor.getInt(conIndex));
                    intelligence = Integer.toString(cursor.getInt(intIndex));
                    wisdom = Integer.toString(cursor.getInt(wisIndex));
                    charisma = Integer.toString(cursor.getInt(chaIndex));

                    base_attack = Integer.toString(cursor.getInt(attackIndex));
                    fortitude = Integer.toString(cursor.getInt(fortIndex));
                    reflex = Integer.toString(cursor.getInt(refIndex));
                    will = Integer.toString(cursor.getInt(willIndex));

                    money = Integer.toString(cursor.getInt(moneyIndex));
                    light_load = Integer.toString(cursor.getInt(lightLoadIndex));
                    medium_load = Integer.toString(cursor.getInt(medLoadIndex));
                    heavy_load = Integer.toString(cursor.getInt(heavyLoadIndex));

                    ac = Integer.toString(cursor.getInt(acIndex));
                    hp_max = Integer.toString(cursor.getInt(hpMaxIndex));
                    hp_curr = Integer.toString(cursor.getInt(hpCurrIndex));
                } finally {
                    cursor.close();
                }
            } finally {
                db.close();
            }
        }

        private void setGender() {
            if (gender == CharacterContract.GENDER_MALE) {
                mViewHolder.mGenderView.setText(R.string.male);
            } else {
                mViewHolder.mGenderView.setText(R.string.female);
            }
        }

        private void setRace() {
            switch (race) {
                case 0:
                    mViewHolder.mRaceView.setText(R.string.human);
                    break;
                case 1:
                    mViewHolder.mRaceView.setText(R.string.elf);
                    break;
                default:
                    mViewHolder.mRaceView.setText(R.string.dwarf);
                    break;
            }
        }

        private void setAlignment() {
            switch (alignment) {
                case CharacterContract.ALIGN_LG:
                    mViewHolder.mAlignmentView.setText(R.string.lawful_good);
                    break;
                case CharacterContract.ALIGN_LN:
                    mViewHolder.mAlignmentView.setText(R.string.lawful_neutral);
                    break;
                case CharacterContract.ALIGN_LE:
                    mViewHolder.mAlignmentView.setText(R.string.lawful_evil);
                    break;
                case CharacterContract.ALIGN_NG:
                    mViewHolder.mAlignmentView.setText(R.string.neutral_good);
                    break;
                case CharacterContract.ALIGN_N:
                    mViewHolder.mAlignmentView.setText(R.string.neutral);
                    break;
                case CharacterContract.ALIGN_NE:
                    mViewHolder.mAlignmentView.setText(R.string.neutral_evil);
                    break;
                case CharacterContract.ALIGN_CG:
                    mViewHolder.mAlignmentView.setText(R.string.chaotic_good);
                    break;
                case CharacterContract.ALIGN_CN:
                    mViewHolder.mAlignmentView.setText(R.string.chaotic_neutral);
                    break;
                default:
                    mViewHolder.mAlignmentView.setText(R.string.chaotic_evil);
                    break;
            }
        }

        private void setInformation() {
            mViewHolder.mNameView.setText(name);
            mViewHolder.mAgeView.setText(age);
            mViewHolder.mWeightView.setText(weight);
            mViewHolder.mHeightView.setText(height);
            mViewHolder.mReligionView.setText(religion);

            setGender();
            setRace();
            setAlignment();

            mViewHolder.mStrengthView.setText(strength);
            mViewHolder.mDexterityView.setText(dexterity);
            mViewHolder.mConstitutionView.setText(constitution);
            mViewHolder.mIntelligenceView.setText(intelligence);
            mViewHolder.mWisdomView.setText(wisdom);
            mViewHolder.mCharismaView.setText(charisma);

            mViewHolder.mBaseAttackView.setText(base_attack);
            mViewHolder.mFortitudeView.setText(fortitude);
            mViewHolder.mReflexView.setText(reflex);
            mViewHolder.mWillView.setText(will);

            mViewHolder.mMoneyView.setText(money);
            mViewHolder.mLightLoadView.setText(light_load);
            mViewHolder.mMediumLoadView.setText(medium_load);
            mViewHolder.mHeavyLoadView.setText(heavy_load);

            mViewHolder.mACView.setText(ac);
            mViewHolder.mHPMaxView.setText(hp_max);
            mViewHolder.mHPCurrentView.setText(hp_curr);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView;
        public TextView mGenderView;
        public TextView mRaceView;
        public TextView mAgeView;
        public TextView mWeightView;
        public TextView mHeightView;
        public TextView mReligionView;
        public TextView mAlignmentView;

        public TextView mStrengthView;
        public TextView mDexterityView;
        public TextView mConstitutionView;
        public TextView mIntelligenceView;
        public TextView mWisdomView;
        public TextView mCharismaView;

        public TextView mBaseAttackView;
        public TextView mFortitudeView;
        public TextView mReflexView;
        public TextView mWillView;

        public TextView mMoneyView;
        public TextView mLightLoadView;
        public TextView mMediumLoadView;
        public TextView mHeavyLoadView;

        public TextView mACView;
        public TextView mHPMaxView;
        public TextView mHPCurrentView;

        public ViewHolder(View view) {
            super(view);

            mNameView = (TextView) view.findViewById(R.id.character_name);
            mGenderView = (TextView) view.findViewById(R.id.character_gender);
            mRaceView = (TextView) view.findViewById(R.id.character_race);
            mAgeView = (TextView) view.findViewById(R.id.character_age);
            mWeightView = (TextView) view.findViewById(R.id.character_weight);
            mHeightView = (TextView) view.findViewById(R.id.character_height);
            mReligionView = (TextView) view.findViewById(R.id.character_religion);
            mAlignmentView = (TextView) view.findViewById(R.id.character_alignment);

            mStrengthView = (TextView) view.findViewById(R.id.ability_strength);
            mDexterityView = (TextView) view.findViewById(R.id.ability_dexterity);
            mConstitutionView = (TextView) view.findViewById(R.id.ability_constitution);
            mIntelligenceView = (TextView) view.findViewById(R.id.ability_intelligence);
            mWisdomView = (TextView) view.findViewById(R.id.ability_wisdom);
            mCharismaView = (TextView) view.findViewById(R.id.ability_charisma);

            mBaseAttackView = (TextView) view.findViewById(R.id.character_attack_bonus);
            mFortitudeView = (TextView) view.findViewById(R.id.character_fort);
            mReflexView = (TextView) view.findViewById(R.id.character_ref);
            mWillView = (TextView) view.findViewById(R.id.character_will);

            mMoneyView = (TextView) view.findViewById(R.id.character_money);
            mLightLoadView = (TextView) view.findViewById(R.id.character_light_load);
            mMediumLoadView = (TextView) view.findViewById(R.id.character_medium_load);
            mHeavyLoadView = (TextView) view.findViewById(R.id.character_heavy_load);

            mACView = (TextView) view.findViewById(R.id.character_ac);
            mHPMaxView = (TextView) view.findViewById(R.id.character_hp_max);
            mHPCurrentView = (TextView) view.findViewById(R.id.character_hp_current);
        }
    }
}
