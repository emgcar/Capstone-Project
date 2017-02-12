package com.brave_bunny.dndhelper.create;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.getClassStats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.getFirstLevelStats;

/**
 * Created by Jemma on 1/17/2017.
 */

public class CreateActivityFragment extends Fragment {
    public static final String PAGE_NUMBER = "page_number";
    public static final String ROW_INDEX = "row_index";

    public static final int PAGE_BASE = 1;
    public static final int PAGE_CLASS = 2;
    public static final int PAGE_SKILL = 3;
    public static final int PAGE_ITEMS = 4;
    public static final int PAGE_DETAIL = 5;

    private long index;
    private View rootView;

    OnClassSelectedListener mClassCallback;
    OnAlignSelectedListener mAlignCallback;

    CreateBaseViewHolder baseViewHolder;
    CreateClassViewHolder classViewHolder;
    CreateSkillFeatViewHolder skillViewHolder;
    CreateDetailViewHolder detailViewHolder;

    public interface OnClassSelectedListener {
        public void onClassSelected(int position);
    }

    public interface OnAlignSelectedListener {
        public void onAlignSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mClassCallback = (OnClassSelectedListener) activity;
            mAlignCallback = (OnAlignSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClassSelectedListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAbilityScores();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int pageNumber = args.getInt(PAGE_NUMBER);
        index = args.getLong(ROW_INDEX);

        switch (pageNumber) {
            case PAGE_BASE:
                rootView = inflater.inflate(R.layout.fragment_create_base, container, false);
                baseViewHolder = new CreateBaseViewHolder(rootView);
                create_base();
                break;
            case PAGE_CLASS:
                rootView = inflater.inflate(R.layout.fragment_create_class, container, false);
                classViewHolder = new CreateClassViewHolder(rootView);
                create_class();
                break;
            case PAGE_SKILL:
                rootView = inflater.inflate(R.layout.fragment_create_skills, container, false);
                skillViewHolder = new CreateSkillFeatViewHolder(rootView);
                create_class();
                break;
            case PAGE_ITEMS:
                rootView = inflater.inflate(R.layout.fragment_create_items, container, false);
                break;
            case PAGE_DETAIL:
                rootView = inflater.inflate(R.layout.fragment_create_detail, container, false);
                detailViewHolder = new CreateDetailViewHolder(rootView);
                create_detail();
                break;
        }
        return rootView;
    }

    private void create_base() {
        baseViewHolder.mNameText.setText(getCharacterString(InProgressContract.CharacterEntry.COLUMN_NAME));
        baseViewHolder.mNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputName(editable);
            }
        });

        int value = getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_GENDER);
        if (value == CharacterContract.GENDER_FEMALE) {
            baseViewHolder.mGenderToggle.performClick();
        }
        baseViewHolder.mGenderToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int gender;
                if (isChecked) {
                    gender = CharacterContract.GENDER_FEMALE;
                } else {
                    gender = CharacterContract.GENDER_MALE;
                }
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_GENDER, gender);
            }
        });

        baseViewHolder.mClassSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID));
        baseViewHolder.mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InProgressCharacterUtil.updateClassValues(getContext(), index, i);
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID, i);
                mClassCallback.onClassSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        baseViewHolder.mRaceSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_RACE_ID));
        baseViewHolder.mRaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_RACE_ID, i);
                updateAbilityScores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateAbilityScores();

        baseViewHolder.mAlignSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_ALIGN));
        baseViewHolder.mAlignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_ALIGN, i);
                mAlignCallback.onAlignSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //TODO don't allow saving of same name characters
    //TODO update UI to notify users of this
    private void inputName(Editable editable) {
        ContentValues values = new ContentValues();

        String characterName = editable.toString();
        values.put(InProgressContract.CharacterEntry.COLUMN_NAME, characterName);

        InProgressCharacterUtil.updateInProgressTable(getContext(), values, index);
    }

    private void inputSpinnerValue(String column, int classId) {

        ContentValues values = new ContentValues();
        values.put(column, classId);

        InProgressCharacterUtil.updateInProgressTable(getContext(), values, index);
    }

    public String getCharacterString(String column) {
        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), index);
        return values.getAsString(column);
    }

    public int getSpinnerValue(String column) {
        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), index);
        if (values.get(column) == null)
            return 0;
        return values.getAsInteger(column);
    }

    //TODO find way to change HP on class page when CON stat changes
    //TODO: find way to change max spells when INT stat changes
    public void updateAbilityScores() {
        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), index);
        if (baseViewHolder != null) {
            setAbilityScore(baseViewHolder.mStrText,
                    InProgressCharacterUtil.getTotalStrengthScore(values));
            setAbilityScore(baseViewHolder.mDexText,
                    InProgressCharacterUtil.getTotalDexterityScore(values));
            setAbilityScore(baseViewHolder.mConText,
                    InProgressCharacterUtil.getTotalConstitutionScore(values));
            setAbilityScore(baseViewHolder.mIntText,
                    InProgressCharacterUtil.getTotalIntelligenceScore(values));
            setAbilityScore(baseViewHolder.mWisText,
                    InProgressCharacterUtil.getTotalWisdomScore(values));
            setAbilityScore(baseViewHolder.mChaText,
                    InProgressCharacterUtil.getTotalCharismaScore(values));
        }
    }

    //TODO set content description
    public void setAbilityScore(TextView view, int score) {
        String scoreString = "";
        if (score != -1) {
            scoreString = Integer.toString(score);
        }
        view.setText(scoreString);
    }

    public void create_class() {
        int classSelection = getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        update_class(classSelection);
    }

    public void create_class(int classSelection) {
        update_class(classSelection);
    }

    //TODO: add stat buffs to ref, fort, will
    //TODO: enable skill button on class selection
    //TODO: enable inventory buttons on class selection
    public void update_class(int classSelection) {
        Bundle args = getArguments();
        int pageNumber = args.getInt(PAGE_NUMBER);
        index = args.getLong(ROW_INDEX);

        if (pageNumber == PAGE_CLASS && classViewHolder != null) {
            classViewHolder.mDeityButton.setVisibility(View.GONE);
            classViewHolder.mSpellsButton.setVisibility(View.GONE);
            classViewHolder.mFamiliarButton.setVisibility(View.GONE);

            ContentValues values = getFirstLevelStats(getContext(), classSelection);

            int baseAttack;
            String baseAttackString;

            int fortSave;
            String fortSaveString;

            int refSave;
            String refSaveString;

            int willSave;
            String willSaveString;

            switch (classSelection) {
                case RulesClassesUtils.CLASS_CLERIC:
                    classViewHolder.mClassText.setText(getString(R.string.cleric));
                    classViewHolder.mDeityButton.setVisibility(View.VISIBLE);
                    setHPText(classSelection);
                    baseAttack = values.getAsInteger(RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1);
                    baseAttackString = Integer.toString(baseAttack);
                    fortSave = values.getAsInteger(RulesContract.ClericEntry.COLUMN_FORT);
                    fortSaveString = Integer.toString(fortSave);
                    refSave = values.getAsInteger(RulesContract.ClericEntry.COLUMN_REF);
                    refSaveString = Integer.toString(refSave);
                    willSave = values.getAsInteger(RulesContract.ClericEntry.COLUMN_WILL);
                    willSaveString = Integer.toString(willSave);
                    break;
                case RulesClassesUtils.CLASS_FIGHTER:
                    classViewHolder.mClassText.setText(getString(R.string.fighter));
                    setHPText(classSelection);
                    baseAttack = values.getAsInteger(RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1);
                    baseAttackString = Integer.toString(baseAttack);
                    fortSave = values.getAsInteger(RulesContract.FighterEntry.COLUMN_FORT);
                    fortSaveString = Integer.toString(fortSave);
                    refSave = values.getAsInteger(RulesContract.FighterEntry.COLUMN_REF);
                    refSaveString = Integer.toString(refSave);
                    willSave = values.getAsInteger(RulesContract.FighterEntry.COLUMN_WILL);
                    willSaveString = Integer.toString(willSave);
                    break;
                case RulesClassesUtils.CLASS_ROGUE:
                    classViewHolder.mClassText.setText(getString(R.string.rogue));
                    setHPText(classSelection);
                    baseAttack = values.getAsInteger(RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1);
                    baseAttackString = Integer.toString(baseAttack);
                    fortSave = values.getAsInteger(RulesContract.RogueEntry.COLUMN_FORT);
                    fortSaveString = Integer.toString(fortSave);
                    refSave = values.getAsInteger(RulesContract.RogueEntry.COLUMN_REF);
                    refSaveString = Integer.toString(refSave);
                    willSave = values.getAsInteger(RulesContract.RogueEntry.COLUMN_WILL);
                    willSaveString = Integer.toString(willSave);
                    break;
                case RulesClassesUtils.CLASS_WIZARD:
                    classViewHolder.mClassText.setText(getString(R.string.wizard));
                    setHPText(classSelection);
                    baseAttack = values.getAsInteger(RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1);
                    baseAttackString = Integer.toString(baseAttack);
                    fortSave = values.getAsInteger(RulesContract.WizardEntry.COLUMN_FORT);
                    fortSaveString = Integer.toString(fortSave);
                    refSave = values.getAsInteger(RulesContract.WizardEntry.COLUMN_REF);
                    refSaveString = Integer.toString(refSave);
                    willSave = values.getAsInteger(RulesContract.WizardEntry.COLUMN_WILL);
                    willSaveString = Integer.toString(willSave);
                    classViewHolder.mSpellsButton.setVisibility(View.VISIBLE);
                    classViewHolder.mFamiliarButton.setVisibility(View.VISIBLE);
                    break;
                default:
                    baseAttackString = "";
                    fortSaveString = "";
                    refSaveString = "";
                    willSaveString = "";
            }

            classViewHolder.mBaseAttackText.setText(baseAttackString);
            classViewHolder.mFortText.setText(fortSaveString);
            classViewHolder.mRefText.setText(refSaveString);
            classViewHolder.mWillText.setText(willSaveString);

        }
    }

    // TODO: update on ability score change
    public void setHPText(int classSelection) {
        ContentValues classValues = getClassStats(getContext(), classSelection);
        int hitPoints = RulesClassesUtils.getClassHitDie(classValues);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), index);
        int conScore = InProgressCharacterUtil.getCharacterCon(values);
        if (conScore != -1) {
            hitPoints += RulesCharacterUtils.scoreToModifier(conScore);
        }

        String hitPointsString = Integer.toString(hitPoints);
        classViewHolder.mHPText.setText(hitPointsString);
    }

    //TODO: change chosen domain columns
    public void update_align(int classSelection) {
        if (classSelection == 0) {
            classViewHolder.mDeityButton.setEnabled(false);
        } else {
            classViewHolder.mDeityButton.setEnabled(true);
        }
    }

    public void create_detail() {
        //TODO set max string length
        detailViewHolder.mAgeText.setText(getCharacterString(InProgressContract.CharacterEntry.COLUMN_AGE));
        detailViewHolder.mAgeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputDetail(editable, InProgressContract.CharacterEntry.COLUMN_AGE);
            }
        });

        //TODO set max string length
        detailViewHolder.mWeightText.setText(getCharacterString(InProgressContract.CharacterEntry.COLUMN_WEIGHT));
        detailViewHolder.mWeightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputDetail(editable, InProgressContract.CharacterEntry.COLUMN_WEIGHT);
            }
        });

        //TODO set max string length
        detailViewHolder.mHeightText.setText(getCharacterString(InProgressContract.CharacterEntry.COLUMN_HEIGHT));
        detailViewHolder.mHeightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputDetail(editable, InProgressContract.CharacterEntry.COLUMN_HEIGHT);
            }
        });
    }

    private void inputDetail(Editable editable, String column) {
        ContentValues values = new ContentValues();

        String characterName = editable.toString();
        values.put(column, characterName);

        InProgressCharacterUtil.updateInProgressTable(getContext(), values, index);
    }

    public void updateSkillPage(int classChoice) {
        if (skillViewHolder == null) return;

        if (classChoice == 0) {
            skillViewHolder.mSkillButton.setEnabled(false);
        } else {
            skillViewHolder.mSkillButton.setEnabled(true);
        }
    }

    public static class CreateBaseViewHolder extends RecyclerView.ViewHolder {
        public EditText mNameText;
        public ToggleButton mGenderToggle;
        public Spinner mClassSpinner;
        public Spinner mRaceSpinner;
        public Spinner mAlignSpinner;

        public TextView mStrText;
        public TextView mDexText;
        public TextView mConText;
        public TextView mIntText;
        public TextView mWisText;
        public TextView mChaText;

        public CreateBaseViewHolder(View view) {
            super(view);

            mNameText = (EditText) view.findViewById(R.id.character_name);
            mGenderToggle = (ToggleButton) view.findViewById(R.id.gender_toggle);
            mClassSpinner = (Spinner) view.findViewById(R.id.class_spinner);
            mRaceSpinner = (Spinner) view.findViewById(R.id.race_spinner);
            mAlignSpinner = (Spinner) view.findViewById(R.id.align_spinner);

            mStrText = (TextView) view.findViewById(R.id.ability_strength);
            mDexText = (TextView) view.findViewById(R.id.ability_dexterity);
            mConText = (TextView) view.findViewById(R.id.ability_constitution);
            mIntText = (TextView) view.findViewById(R.id.ability_intelligence);
            mWisText = (TextView) view.findViewById(R.id.ability_wisdom);
            mChaText = (TextView) view.findViewById(R.id.ability_charisma);
        }
    }

    public static class CreateClassViewHolder extends RecyclerView.ViewHolder {
        public TextView mClassText;
        public TextView mHPText;

        public Button mDeityButton;
        public Button mSpellsButton;
        public Button mFamiliarButton;

        public TextView mBaseAttackText;
        public TextView mFortText;
        public TextView mRefText;
        public TextView mWillText;

        public CreateClassViewHolder(View view) {
            super(view);

            mClassText = (TextView) view.findViewById(R.id.chosen_class);
            mHPText = (TextView) view.findViewById(R.id.hp);

            mDeityButton = (Button) view.findViewById(R.id.select_deity);
            mSpellsButton = (Button) view.findViewById(R.id.select_spells);
            mFamiliarButton = (Button) view.findViewById(R.id.select_familiar);

            mBaseAttackText = (TextView) view.findViewById(R.id.base_attack);
            mFortText = (TextView) view.findViewById(R.id.fort_save);
            mRefText = (TextView) view.findViewById(R.id.ref_save);
            mWillText = (TextView) view.findViewById(R.id.will_save);
        }
    }

    public static class CreateSkillFeatViewHolder extends RecyclerView.ViewHolder {
        public Button mSkillButton;
        public Button mFeatButton;

        public CreateSkillFeatViewHolder(View view) {
            super(view);

            mSkillButton = (Button) view.findViewById(R.id.skill_button);
            mFeatButton = (Button) view.findViewById(R.id.feat_button);
        }
    }

    public static class CreateDetailViewHolder extends RecyclerView.ViewHolder {
        public EditText mAgeText;
        public EditText mWeightText;
        public EditText mHeightText;

        public CreateDetailViewHolder(View view) {
            super(view);

            mAgeText = (EditText) view.findViewById(R.id.character_age);
            mWeightText = (EditText) view.findViewById(R.id.character_weight);
            mHeightText = (EditText) view.findViewById(R.id.character_height);
        }
    }
}