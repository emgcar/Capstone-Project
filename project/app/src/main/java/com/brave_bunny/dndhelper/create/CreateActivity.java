package com.brave_bunny.dndhelper.create;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.CharacterContract;

/**
 * Created by Jemma on 8/7/2016.
 */
public class CreateActivity extends AppCompatActivity {

    long index;


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
            ContentValues blankCharacterValues = Utility.setNewInProgressContentValues();
            index = Utility.insertValuesInTable(this, CharacterContract.InProgressEntry.TABLE_NAME,
                    blankCharacterValues);
        }
    }

    private void initializePagerAdapter() {
        CreateCharacterPagerAdapter pagerAdapter =
                new CreateCharacterPagerAdapter(getSupportFragmentManager(), index);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_create);
        viewPager.setAdapter(pagerAdapter);
    }



    public void createCharacter(View view) {
        int characterState = Utility.checkStateOfCharacterChoices(this, index);

        if (characterState == Utility.STATE_COMPLETE) {
            ContentValues values = Utility.getCharacterContentValues(this, index);
            Utility.insertValuesInTable(this, CharacterContract.CharacterEntry.TABLE_NAME, values);
            Utility.deleteValuesFromTable(this, CharacterContract.InProgressEntry.TABLE_NAME, index);

            this.finish();
        }
    }

    public void launchAbilityScoreSelector(View view) {

        Intent abilityActivity = new Intent(this, AbilityActivity.class);

        ContentValues values = Utility.getInProgressContentValues(this, index);
        abilityActivity.putExtra(AbilityActivity.inprogressValues, values);
        abilityActivity.putExtra(AbilityActivity.indexValue, index);

        startActivity(abilityActivity);
    }



    public class CreateCharacterPagerAdapter extends FragmentPagerAdapter {

        final private int numberOfCreateCharacterFragments = 7;
        private long index;

        public CreateCharacterPagerAdapter(FragmentManager fm, long id) {
            super(fm);
            index = id;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new CreateActivityFragment();
            Bundle args = new Bundle();
            args.putInt(CreateActivityFragment.PAGE_NUMBER, position + 1);
            args.putLong(CreateActivityFragment.ROW_INDEX, index);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return numberOfCreateCharacterFragments;
        }
    }

    public static class CreateActivityFragment extends Fragment {
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
            nameText.setText(getCharacterName());
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
        }

        private void inputName(Editable editable) {
            ContentValues values = new ContentValues();

            String characterName = editable.toString();
            values.put(CharacterContract.InProgressEntry.COLUMN_NAME, characterName);

            Utility.updateValuesInTable(getContext(), CharacterContract.InProgressEntry.TABLE_NAME,
                    values, index);
        }

        public String getCharacterName() {
            ContentValues values = Utility.getInProgressContentValues(getContext(), index);
            return values.getAsString(CharacterContract.InProgressEntry.COLUMN_NAME);
        }
    }
}
