package com.brave_bunny.dndhelper.create;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

/**
 * Created by Jemma on 1/17/2017.
 */

public class CreateActivityFragment extends Fragment {
    public static final String PAGE_NUMBER = "page_number";
    public static final String ROW_INDEX = "row_index";

    public static final int PAGE_BASE = 1;
    public static final int PAGE_CLASS = 2;
    public static final int PAGE_SKILL = 3;
    public static final int PAGE_WEAPONS = 4;
    public static final int PAGE_ITEMS = 5;
    public static final int PAGE_DETAIL = 6;
    public static final int PAGE_FINISH = 7;

    private long index;
    private View rootView;

    OnClassSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnClassSelectedListener {
        public void onClassSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClassSelectedListener) activity;
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
                create_base();
                break;
            case PAGE_CLASS:
                rootView = inflater.inflate(R.layout.fragment_create_class, container, false);
                create_class();
                break;
            case PAGE_SKILL:
                rootView = inflater.inflate(R.layout.fragment_create_skills, container, false);
                break;
            case PAGE_WEAPONS:
                rootView = inflater.inflate(R.layout.fragment_create_weapons, container, false);
                break;
            case PAGE_ITEMS:
                rootView = inflater.inflate(R.layout.fragment_create_items, container, false);
                break;
            case PAGE_DETAIL:
                rootView = inflater.inflate(R.layout.fragment_create_detail, container, false);
                break;
            case PAGE_FINISH:
                rootView = inflater.inflate(R.layout.fragment_create_finish, container, false);
                break;
        }
        return rootView;
    }

    private void create_base() {
        EditText nameText = (EditText) rootView.findViewById(R.id.character_name);
        nameText.setText(getCharacterString(InProgressContract.CharacterEntry.COLUMN_NAME));
        nameText.addTextChangedListener(new TextWatcher() {
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

        ToggleButton genderToggle = (ToggleButton) rootView.findViewById(R.id.gender_toggle);
        int value = getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_GENDER);
        if (value == CharacterContract.GENDER_FEMALE) {
            genderToggle.performClick();
        }
        genderToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        Spinner classSpinner = (Spinner) rootView.findViewById(R.id.class_spinner);
        classSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID));
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID, i);
                mCallback.onClassSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner raceSpinner = (Spinner) rootView.findViewById(R.id.race_spinner);
        raceSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_RACE_ID));
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_RACE_ID, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateAbilityScores();

        Spinner alignSpinner = (Spinner) rootView.findViewById(R.id.align_spinner);
        alignSpinner.setSelection(getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_ALIGN));
        alignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputSpinnerValue(InProgressContract.CharacterEntry.COLUMN_ALIGN, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void inputName(Editable editable) {
        ContentValues values = new ContentValues();

        String characterName = editable.toString();
        values.put(InProgressContract.CharacterEntry.COLUMN_NAME, characterName);

        InProgressUtil.updateInProgressTable(getContext(),
                InProgressContract.CharacterEntry.TABLE_NAME, values, index);
    }

    private void inputSpinnerValue(String column, int classId) {

        ContentValues values = new ContentValues();
        values.put(column, classId);

        InProgressUtil.updateInProgressTable(getContext(),
                InProgressContract.CharacterEntry.TABLE_NAME, values, index);
    }

    public String getCharacterString(String column) {
        ContentValues values = InProgressUtil.getInProgressRow(getContext(), index);
        return values.getAsString(column);
    }

    public int getSpinnerValue(String column) {
        ContentValues values = InProgressUtil.getInProgressRow(getContext(), index);
        if (values.get(column) == null)
            return 0;
        return values.getAsInteger(column);
    }

    public void updateAbilityScores() {
        TextView strText = (TextView) rootView.findViewById(R.id.ability_strength);
        if (strText != null) {
            strText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_STR));

            TextView dexText = (TextView) rootView.findViewById(R.id.ability_dexterity);
            dexText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_DEX));

            TextView conText = (TextView) rootView.findViewById(R.id.ability_constitution);
            conText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_CON));

            TextView intText = (TextView) rootView.findViewById(R.id.ability_intelligence);
            intText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_INT));

            TextView wisText = (TextView) rootView.findViewById(R.id.ability_wisdom);
            wisText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_WIS));

            TextView chaText = (TextView) rootView.findViewById(R.id.ability_charisma);
            chaText.setText(getAbilityScoreText(InProgressContract.CharacterEntry.COLUMN_CHA));
        }
    }

    public String getAbilityScoreText(String column) {
        ContentValues values = InProgressUtil.getInProgressRow(getContext(), index);
        int score = values.getAsInteger(column);

        if (score == -1) {
            return "";
        } else {
            return Integer.toString(score);
        }
    }

    public void create_class() {
        int classSelection = getSpinnerValue(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);

        Bundle args = getArguments();
        int pageNumber = args.getInt(PAGE_NUMBER);
        index = args.getLong(ROW_INDEX);

        if (pageNumber == PAGE_CLASS) {
            Button deityButton = (Button) rootView.findViewById(R.id.select_deity);
            deityButton.setVisibility(View.INVISIBLE);

            Button spellsButton = (Button) rootView.findViewById(R.id.select_spells);
            spellsButton.setVisibility(View.INVISIBLE);

            Button familiarButton = (Button) rootView.findViewById(R.id.select_familiar);
            familiarButton.setVisibility(View.INVISIBLE);

            switch (classSelection) {
                case RulesUtils.CLASS_CLERIC:
                    deityButton.setVisibility(View.VISIBLE);
                    break;
                case RulesUtils.CLASS_FIGHTER:
                    break;
                case RulesUtils.CLASS_ROGUE:
                    break;
                case RulesUtils.CLASS_WIZARD:
                    spellsButton.setVisibility(View.VISIBLE);
                    familiarButton.setVisibility(View.VISIBLE);
                    break;
                default:
            }

        }
    }
}