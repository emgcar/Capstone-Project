package com.brave_bunny.dndhelper.create.abilities;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY1;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY2;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY3;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY4;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY5;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITY6;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYCHA;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYCON;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYDEX;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYINT;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYSTR;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.ABILITYWIS;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.getInProgressRow;


public class AbilityFragment extends Fragment {

    private static AbilityViewHolder mViewHolder;
    private static ContentValues mValues;
    private static long mIndex;

    private static int currentSelection;

    private static TextView getTextView(int view) {
        switch (view) {
            case ABILITY1:
                return mViewHolder.mOption1Text;
            case ABILITY2:
                return mViewHolder.mOption2Text;
            case ABILITY3:
                return mViewHolder.mOption3Text;
            case ABILITY4:
                return mViewHolder.mOption4Text;
            case ABILITY5:
                return mViewHolder.mOption5Text;
            case ABILITY6:
                return mViewHolder.mOption6Text;
            case ABILITYSTR:
                return mViewHolder.mStrText;
            case ABILITYDEX:
                return mViewHolder.mDexText;
            case ABILITYCON:
                return mViewHolder.mConText;
            case ABILITYINT:
                return mViewHolder.mIntText;
            case ABILITYWIS:
                return mViewHolder.mWisText;
            case ABILITYCHA:
                return mViewHolder.mChaText;
        }
        return null;
    }

    public AbilityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ability, container, false);
        mViewHolder = new AbilityViewHolder(rootView);
        currentSelection = 0;

        Bundle args = getArguments();
        mValues = (ContentValues) args.get(AbilityActivity.inprogressValues);
        mIndex = (long) args.get(AbilityActivity.indexValue);

        updateTexts();
        setUpOptionListeners();
        setUpAbilityListeners();
        updateRollAbilityButtonState();

        return rootView;
    }

    public void updateTexts() {
        mValues = getInProgressRow(getContext(), mIndex);
        int ability1Score = InProgressCharacterUtil.getCharacterAbility1(mValues);
        int ability2Score = InProgressCharacterUtil.getCharacterAbility2(mValues);
        int ability3Score = InProgressCharacterUtil.getCharacterAbility3(mValues);
        int ability4Score = InProgressCharacterUtil.getCharacterAbility4(mValues);
        int ability5Score = InProgressCharacterUtil.getCharacterAbility5(mValues);
        int ability6Score = InProgressCharacterUtil.getCharacterAbility6(mValues);

        mViewHolder.mOption1Text.setText(Integer.toString(ability1Score));
        mViewHolder.mOption1Text.setContentDescription(Integer.toString(ability1Score));
        mViewHolder.mOption2Text.setText(Integer.toString(ability2Score));
        mViewHolder.mOption2Text.setContentDescription(Integer.toString(ability2Score));
        mViewHolder.mOption3Text.setText(Integer.toString(ability3Score));
        mViewHolder.mOption3Text.setContentDescription(Integer.toString(ability3Score));
        mViewHolder.mOption4Text.setText(Integer.toString(ability4Score));
        mViewHolder.mOption4Text.setContentDescription(Integer.toString(ability4Score));
        mViewHolder.mOption5Text.setText(Integer.toString(ability5Score));
        mViewHolder.mOption5Text.setContentDescription(Integer.toString(ability5Score));
        mViewHolder.mOption6Text.setText(Integer.toString(ability6Score));
        mViewHolder.mOption6Text.setContentDescription(Integer.toString(ability6Score));

        updateAbilityTexts();
        updateRollAbilityButtonState();
    }

    private void updateAbilityTexts() {
        int strConnect = InProgressCharacterUtil.getCharacterStrConnection(mValues);
        int dexConnect = InProgressCharacterUtil.getCharacterDexConnection(mValues);
        int conConnect = InProgressCharacterUtil.getCharacterConConnection(mValues);
        int intConnect = InProgressCharacterUtil.getCharacterIntConnection(mValues);
        int wisConnect = InProgressCharacterUtil.getCharacterWisConnection(mValues);
        int chaConnect = InProgressCharacterUtil.getCharacterChaConnection(mValues);

        if (strConnect != -1) {
            int strScore = InProgressCharacterUtil.getCharacterStr(mValues);
            mViewHolder.mStrText.setText(Integer.toString(strScore));
            mViewHolder.mStrText.setContentDescription(Integer.toString(strScore));
            blankText(strConnect);
        } else {
            mViewHolder.mStrText.setText(R.string.option);
            mViewHolder.mStrText.setContentDescription(getString(R.string.option));
        }

        if (dexConnect != -1) {
            int dexScore = InProgressCharacterUtil.getCharacterDex(mValues);
            mViewHolder.mDexText.setText(Integer.toString(dexScore));
            mViewHolder.mDexText.setContentDescription(Integer.toString(dexScore));
            blankText(dexConnect);
        } else {
            mViewHolder.mDexText.setText(R.string.option);
            mViewHolder.mDexText.setContentDescription(getString(R.string.option));
        }

        if (conConnect != -1) {
            int conScore = InProgressCharacterUtil.getCharacterCon(mValues);
            mViewHolder.mConText.setText(Integer.toString(conScore));
            mViewHolder.mConText.setContentDescription(Integer.toString(conScore));
            blankText(conConnect);
        } else {
            mViewHolder.mConText.setText(R.string.option);
            mViewHolder.mConText.setContentDescription(getString(R.string.option));
        }

        if (intConnect != -1) {
            int intScore = InProgressCharacterUtil.getCharacterInt(mValues);
            mViewHolder.mIntText.setText(Integer.toString(intScore));
            mViewHolder.mIntText.setContentDescription(Integer.toString(intScore));
            blankText(intConnect);
        } else {
            mViewHolder.mIntText.setText(R.string.option);
            mViewHolder.mIntText.setContentDescription(getString(R.string.option));
        }

        if (wisConnect != -1) {
            int wisScore = InProgressCharacterUtil.getCharacterWis(mValues);
            mViewHolder.mWisText.setText(Integer.toString(wisScore));
            mViewHolder.mWisText.setContentDescription(Integer.toString(wisScore));
            blankText(wisConnect);
        } else {
            mViewHolder.mWisText.setText(R.string.option);
            mViewHolder.mWisText.setContentDescription(getString(R.string.option));
        }

        if (chaConnect != -1) {
            int chaScore = InProgressCharacterUtil.getCharacterCha(mValues);
            mViewHolder.mChaText.setText(Integer.toString(chaScore));
            mViewHolder.mChaText.setContentDescription(Integer.toString(chaScore));
            blankText(chaConnect);
        } else {
            mViewHolder.mChaText.setText(R.string.option);
            mViewHolder.mChaText.setContentDescription(getString(R.string.option));
        }
    }

    private void setUpOptionListeners() {
        setClickListener(mViewHolder.mOption1Text, ABILITY1);
        setClickListener(mViewHolder.mOption2Text, ABILITY2);
        setClickListener(mViewHolder.mOption3Text, ABILITY3);
        setClickListener(mViewHolder.mOption4Text, ABILITY4);
        setClickListener(mViewHolder.mOption5Text, ABILITY5);
        setClickListener(mViewHolder.mOption6Text, ABILITY6);

    }

    private void setUpAbilityListeners() {
        setClickListener(mViewHolder.mStrText, ABILITYSTR);
        setClickListener(mViewHolder.mDexText, ABILITYDEX);
        setClickListener(mViewHolder.mConText, ABILITYCON);
        setClickListener(mViewHolder.mIntText, ABILITYINT);
        setClickListener(mViewHolder.mWisText, ABILITYWIS);
        setClickListener(mViewHolder.mChaText, ABILITYCHA);

    }

    private void setClickListener(TextView view, final int clickLocation) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickDecision(clickLocation);
            }
        });
    }

    private void handleClickDecision(int clickLocation) {
        if (currentSelection == 0) {
            handleClickWithNoPreviousSelection(clickLocation);
        } else {
            handleClickWithPreviousSelection(clickLocation);
        }
    }

    private void handleClickWithNoPreviousSelection(int clickLocation) {
        if (isAbilityChoice(clickLocation)) {
            if (abilityHasConnection(clickLocation)) {
                currentSelection = clickLocation;
                selectView(getTextView(clickLocation));
            }
        } else {
            if (optionIsUnassigned(clickLocation)) {
                currentSelection = clickLocation;
                selectView(getTextView(clickLocation));
            }
        }
    }

    private void handleClickWithPreviousSelection(int clickLocation) {
        if (isAbilityChoice(currentSelection)) {
            handleClickWithAbilityAsPreviousSelection(clickLocation);
        } else {
            handleClickWithOptionAsPreviousSelection(clickLocation);
        }
    }

    private void handleClickWithAbilityAsPreviousSelection(int clickLocation) {
        if (isAbilityChoice(clickLocation)) {
            if (clickLocation == currentSelection) {
                deselectView(getTextView(clickLocation));
                currentSelection = 0;
            } else {
                deselectView(getTextView(currentSelection));
                swapAbilityConnections(clickLocation);
                currentSelection = 0;
                updateInprogressRow();
            }
        } else if (optionIsUnassigned(clickLocation)) {
            deselectView(getTextView(currentSelection));
            currentSelection = clickLocation;
            selectView(getTextView(clickLocation));
        }
    }

    public void swapAbilityConnections(int clickLocation) {
        if (abilityHasConnection(clickLocation)) {
            int oldConnect = getConnection(clickLocation);
            resetConnection(oldConnect);
        }
        setConnection(clickLocation, getConnection(currentSelection));
        setConnection(currentSelection, -1);
    }

    private void resetConnection(int selection) {
        int score;

        switch (selection) {
            case ABILITY1:
                score = InProgressCharacterUtil.getCharacterAbility1(mValues);
                mViewHolder.mOption1Text.setText(Integer.toString(score));
                mViewHolder.mOption1Text.setContentDescription(Integer.toString(score));
                break;
            case ABILITY2:
                score = InProgressCharacterUtil.getCharacterAbility2(mValues);
                mViewHolder.mOption2Text.setText(Integer.toString(score));
                mViewHolder.mOption2Text.setContentDescription(Integer.toString(score));
                break;
            case ABILITY3:
                score = InProgressCharacterUtil.getCharacterAbility3(mValues);
                mViewHolder.mOption3Text.setText(Integer.toString(score));
                mViewHolder.mOption3Text.setContentDescription(Integer.toString(score));
                break;
            case ABILITY4:
                score = InProgressCharacterUtil.getCharacterAbility4(mValues);
                mViewHolder.mOption4Text.setText(Integer.toString(score));
                mViewHolder.mOption4Text.setContentDescription(Integer.toString(score));
                break;
            case ABILITY5:
                score = InProgressCharacterUtil.getCharacterAbility5(mValues);
                mViewHolder.mOption5Text.setText(Integer.toString(score));
                mViewHolder.mOption5Text.setContentDescription(Integer.toString(score));
                break;
            case ABILITY6:
                score = InProgressCharacterUtil.getCharacterAbility6(mValues);
                mViewHolder.mOption6Text.setText(Integer.toString(score));
                mViewHolder.mOption6Text.setContentDescription(Integer.toString(score));
                break;
            default:
        }
    }

    private int getConnection(int selection) {
        switch (selection) {
            case ABILITYSTR:
                return InProgressCharacterUtil.getCharacterStrConnection(mValues);
            case ABILITYDEX:
                return InProgressCharacterUtil.getCharacterDexConnection(mValues);
            case ABILITYCON:
                return InProgressCharacterUtil.getCharacterConConnection(mValues);
            case ABILITYINT:
                return InProgressCharacterUtil.getCharacterIntConnection(mValues);
            case ABILITYWIS:
                return InProgressCharacterUtil.getCharacterWisConnection(mValues);
            case ABILITYCHA:
                return InProgressCharacterUtil.getCharacterChaConnection(mValues);
            default:
                return -1;
        }
    }

    private void setConnection(int selection, int connection) {
        String score;
        if (connection == -1) {
            score = getString(R.string.option);
        } else {
            score = Integer.toString(getConnectionValue(connection));
        }

        switch (selection) {
            case ABILITYSTR:
                InProgressCharacterUtil.setCharacterStr(mValues, connection);
                mViewHolder.mStrText.setText(score);
                mViewHolder.mStrText.setContentDescription(score);
                break;
            case ABILITYDEX:
                InProgressCharacterUtil.setCharacterDex(mValues, connection);
                mViewHolder.mDexText.setText(score);
                mViewHolder.mDexText.setContentDescription(score);
                break;
            case ABILITYCON:
                InProgressCharacterUtil.setCharacterCon(mValues, connection);
                mViewHolder.mConText.setText(score);
                mViewHolder.mConText.setContentDescription(score);
                break;
            case ABILITYINT:
                InProgressCharacterUtil.setCharacterInt(mValues, connection);
                mViewHolder.mIntText.setText(score);
                mViewHolder.mIntText.setContentDescription(score);
                break;
            case ABILITYWIS:
                InProgressCharacterUtil.setCharacterWis(mValues, connection);
                mViewHolder.mWisText.setText(score);
                mViewHolder.mWisText.setContentDescription(score);
                break;
            case ABILITYCHA:
                InProgressCharacterUtil.setCharacterCha(mValues, connection);
                mViewHolder.mChaText.setText(score);
                mViewHolder.mChaText.setContentDescription(score);
                break;
        }
        blankText(connection);
    }

    private void updateInprogressRow() {
        InProgressCharacterUtil.updateInProgressTable(getContext(), mValues, mIndex);
    }

    private void handleClickWithOptionAsPreviousSelection(int clickLocation) {
        if (isAbilityChoice(clickLocation)) {
            if (abilityHasConnection(clickLocation)) {
                int oldConnect = getConnection(clickLocation);
                resetConnection(oldConnect);
            }
            setConnection(clickLocation, currentSelection);
            deselectView(getTextView(currentSelection));
            currentSelection = 0;
            updateInprogressRow();
        } else {
            if (clickLocation == currentSelection) {
                deselectView(getTextView(currentSelection));
                currentSelection = 0;
            } else {
                if (optionIsUnassigned(clickLocation)) {
                    deselectView(getTextView(currentSelection));
                    currentSelection = clickLocation;
                    selectView(getTextView(clickLocation));
                }
            }
        }
    }

    private boolean abilityHasConnection(int selection) {
        int connection = getConnection(selection);
        return (connection != -1);
    }

    private boolean optionIsUnassigned(int selection) {
        boolean isUnassigned;
        isUnassigned = (InProgressCharacterUtil.getCharacterStrConnection(mValues) != selection);
        isUnassigned &= (InProgressCharacterUtil.getCharacterDexConnection(mValues) != selection);
        isUnassigned &= (InProgressCharacterUtil.getCharacterConConnection(mValues) != selection);
        isUnassigned &= (InProgressCharacterUtil.getCharacterIntConnection(mValues) != selection);
        isUnassigned &= (InProgressCharacterUtil.getCharacterWisConnection(mValues) != selection);
        isUnassigned &= (InProgressCharacterUtil.getCharacterChaConnection(mValues) != selection);
        return isUnassigned;
    }

    public boolean isAbilityChoice(int selection) {
        switch (selection) {
            case ABILITYSTR:
            case ABILITYDEX:
            case ABILITYCON:
            case ABILITYINT:
            case ABILITYWIS:
            case ABILITYCHA:
                return true;
            default:
                return false;
        }
    }

    public void selectView(TextView view) {
        view.setTextColor(getResources().getColor(R.color.colorPrimary));
        view.setTextScaleX(2);
    }

    public void deselectView(TextView view) {
        view.setTextColor(Color.BLACK);
        view.setTextScaleX(1/2);
    }

    private int getConnectionValue(int view) {
        switch (view) {
            case ABILITY1:
                return InProgressCharacterUtil.getCharacterAbility1(mValues);
            case ABILITY2:
                return InProgressCharacterUtil.getCharacterAbility2(mValues);
            case ABILITY3:
                return InProgressCharacterUtil.getCharacterAbility3(mValues);
            case ABILITY4:
                return InProgressCharacterUtil.getCharacterAbility4(mValues);
            case ABILITY5:
                return InProgressCharacterUtil.getCharacterAbility5(mValues);
            case ABILITY6:
                return InProgressCharacterUtil.getCharacterAbility6(mValues);
            default:
                return -1;
        }
    }

    public void blankText(int view) {
        switch (view) {
            case ABILITY1:
                mViewHolder.mOption1Text.setText("");
                mViewHolder.mOption1Text.setContentDescription("");
                break;
            case ABILITY2:
                mViewHolder.mOption2Text.setText("");
                mViewHolder.mOption2Text.setContentDescription("");
                break;
            case ABILITY3:
                mViewHolder.mOption3Text.setText("");
                mViewHolder.mOption3Text.setContentDescription("");
                break;
            case ABILITY4:
                mViewHolder.mOption4Text.setText("");
                mViewHolder.mOption4Text.setContentDescription("");
                break;
            case ABILITY5:
                mViewHolder.mOption5Text.setText("");
                mViewHolder.mOption5Text.setContentDescription("");
                break;
            case ABILITY6:
                mViewHolder.mOption6Text.setText("");
                mViewHolder.mOption6Text.setContentDescription("");
                break;
        }
    }


    private void updateRollAbilityButtonState() {
        mViewHolder.mReRollButton.setEnabled(false);

        int sumOfModifiers;
        int highestTotalScore = 0;

        int firstScore = InProgressCharacterUtil.getCharacterAbility1(mValues);
        int secondScore = InProgressCharacterUtil.getCharacterAbility2(mValues);
        int thirdScore = InProgressCharacterUtil.getCharacterAbility3(mValues);
        int fourthScore = InProgressCharacterUtil.getCharacterAbility4(mValues);
        int fifthScore = InProgressCharacterUtil.getCharacterAbility5(mValues);
        int sixthScore = InProgressCharacterUtil.getCharacterAbility6(mValues);

        if (firstScore > highestTotalScore) highestTotalScore = firstScore;
        if (secondScore > highestTotalScore) highestTotalScore = firstScore;
        if (thirdScore > highestTotalScore) highestTotalScore = thirdScore;
        if (fourthScore > highestTotalScore) highestTotalScore = fourthScore;
        if (fifthScore > highestTotalScore) highestTotalScore = fifthScore;
        if (sixthScore > highestTotalScore) highestTotalScore = sixthScore;

        sumOfModifiers = RulesCharacterUtils.scoreToModifier(firstScore);
        sumOfModifiers += RulesCharacterUtils.scoreToModifier(secondScore);
        sumOfModifiers += RulesCharacterUtils.scoreToModifier(thirdScore);
        sumOfModifiers += RulesCharacterUtils.scoreToModifier(fourthScore);
        sumOfModifiers += RulesCharacterUtils.scoreToModifier(fifthScore);
        sumOfModifiers += RulesCharacterUtils.scoreToModifier(sixthScore);

        // Rule in Players Handbook pg 8
        // allows re-rolling if scores considered too low
        if ((sumOfModifiers <= 0) || (highestTotalScore <= 13)) {
            mViewHolder.mReRollButton.setEnabled(true);
        }
    }

    public static class AbilityViewHolder extends RecyclerView.ViewHolder {
        public TextView mOption1Text;
        public TextView mOption2Text;
        public TextView mOption3Text;
        public TextView mOption4Text;
        public TextView mOption5Text;
        public TextView mOption6Text;

        public TextView mStrText;
        public TextView mDexText;
        public TextView mConText;
        public TextView mIntText;
        public TextView mWisText;
        public TextView mChaText;

        public Button mReRollButton;

        public AbilityViewHolder(View view) {
            super(view);

            mOption1Text = (TextView) view.findViewById(R.id.choice_1);
            mOption2Text = (TextView) view.findViewById(R.id.choice_2);
            mOption3Text = (TextView) view.findViewById(R.id.choice_3);
            mOption4Text = (TextView) view.findViewById(R.id.choice_4);
            mOption5Text = (TextView) view.findViewById(R.id.choice_5);
            mOption6Text = (TextView) view.findViewById(R.id.choice_6);

            mStrText = (TextView) view.findViewById(R.id.ability_strength);
            mDexText = (TextView) view.findViewById(R.id.ability_dexterity);
            mConText = (TextView) view.findViewById(R.id.ability_constitution);
            mIntText = (TextView) view.findViewById(R.id.ability_intelligence);
            mWisText = (TextView) view.findViewById(R.id.ability_wisdom);
            mChaText = (TextView) view.findViewById(R.id.ability_charisma);

            mReRollButton = (Button) view.findViewById(R.id.reroll_button);
        }
    }


}
