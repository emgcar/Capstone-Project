package com.brave_bunny.dndhelper.create.base;

import android.content.ClipData;
import android.content.ContentValues;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;


public class AbilityFragment extends Fragment {

    int first;
    int second;
    int third;
    int fourth;
    int fifth;
    int sixth;

    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma;

    int strConnect;
    int dexConnect;
    int conConnect;
    int intConnect;
    int wisConnect;
    int chaConnect;

    boolean firstUsed;
    boolean secondUsed;
    boolean thirdUsed;
    boolean fourthUsed;
    boolean fifthUsed;
    boolean sixthUsed;

    public AbilityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ability, container, false);

        Bundle args = getArguments();
        ContentValues values = (ContentValues) args.get(AbilityActivity.inprogressValues);
        setGlobalVars(values);
        setConnections(rootView);
        setListeners(rootView);

        setPreviousState(rootView);

        return rootView;
    }

    private void setGlobalVars(ContentValues values) {
        first = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_1);
        second = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_2);
        third = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_3);
        fourth = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_4);
        fifth = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_5);
        sixth = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ABILITY_6);

        strength = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_STR);
        dexterity = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_DEX);
        constitution = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CON);
        intelligence = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_INT);
        wisdom = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_WIS);
        charisma = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CHA);
    }

    private void setConnections(View view) {

        strConnect = getConnect(strength, view);
        dexConnect = getConnect(dexterity, view);
        conConnect = getConnect(constitution, view);
        intConnect = getConnect(intelligence, view);
        wisConnect = getConnect(wisdom, view);
        chaConnect = getConnect(charisma, view);
    }

    private int getConnect(int value, View rootView) {

        if (value == -1) {
            return -1;
        } else if (value == first && !firstUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_1);
            view.setVisibility(View.INVISIBLE);
            firstUsed = true;
            return R.id.choice_1;
        } else if (value == second && !secondUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_2);
            view.setVisibility(View.INVISIBLE);
            secondUsed = true;
            return R.id.choice_2;
        } else if (value == third && !thirdUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_3);
            view.setVisibility(View.INVISIBLE);
            thirdUsed = true;
            return R.id.choice_3;
        } else if (value == fourth && !fourthUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_4);
            view.setVisibility(View.INVISIBLE);
            fourthUsed = true;
            return R.id.choice_4;
        } else if (value == fifth && !fifthUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_5);
            view.setVisibility(View.INVISIBLE);
            fifthUsed = true;
            return R.id.choice_5;
        } else if (value == sixth && !sixthUsed) {
            TextView view = (TextView) rootView.findViewById(R.id.choice_6);
            view.setVisibility(View.INVISIBLE);
            sixthUsed = true;
            return R.id.choice_6;
        } else {
            return -1;
        }
    }

    private void setListeners(View rootView) {
        setOptionListeners(rootView, R.id.ability_strength);
        setOptionListeners(rootView, R.id.ability_dexterity);
        setOptionListeners(rootView, R.id.ability_constitution);
        setOptionListeners(rootView, R.id.ability_intelligence);
        setOptionListeners(rootView, R.id.ability_wisdom);
        setOptionListeners(rootView, R.id.ability_charisma);

        setChoiceListeners(rootView, R.id.choice_1, first);
        setChoiceListeners(rootView, R.id.choice_2, second);
        setChoiceListeners(rootView, R.id.choice_3, third);
        setChoiceListeners(rootView, R.id.choice_4, fourth);
        setChoiceListeners(rootView, R.id.choice_5, fifth);
        setChoiceListeners(rootView, R.id.choice_6, sixth);
    }

    private void setPreviousState(View rootView) {
        TextView view;

        if (strConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_strength);
            view.setText(Integer.toString(strength));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(strConnect);
        }

        if (dexConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_dexterity);
            view.setText(Integer.toString(dexterity));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(dexConnect);
        }

        if (conConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_constitution);
            view.setText(Integer.toString(constitution));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(conConnect);
        }

        if (intConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_intelligence);
            view.setText(Integer.toString(intelligence));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(intConnect);
        }

        if (wisConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_wisdom);
            view.setText(Integer.toString(wisdom));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(wisConnect);
        }

        if (chaConnect != -1) {
            view = (TextView) rootView.findViewById(R.id.ability_charisma);
            view.setText(Integer.toString(charisma));
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setTag(chaConnect);
        }
    }

    public void setOptionListeners(View rootView, int id) {
        TextView view = (TextView) rootView.findViewById(id);
        view.setOnDragListener(new ChoiceDragListener());
        view.setOnTouchListener(new ChoiceTouchListener());
        view.setTag("");
    }

    private void setChoiceListeners(View rootView, int id, int score) {
        TextView view = (TextView) rootView.findViewById(id);
        view.setOnDragListener(new ChoiceDragListener());
        view.setOnTouchListener(new ChoiceTouchListener());
        view.setTag(id);
        view.setText(Integer.toString(score));
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                    && !view.getTag().equals("")) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            View view;
            TextView dropTarget;
            Object tag;
            int existingID;
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("choice drag listener", "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("choice drag listener", "ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("choice drag listener", "ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("choice drag listener", "ACTION_DROP");
                    view = (View) dragEvent.getLocalState();
                    dropTarget = (TextView) v;
                    TextView dropped = (TextView) view;
                    tag = dropTarget.getTag();
                    if (tag != null && !tag.equals("")) {
                        existingID = (Integer)tag;
                        getActivity().findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    dropTarget.setText(dropped.getText());
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    dropTarget.setTag(dropped.getTag());
                    if (dropped.getTypeface() == Typeface.DEFAULT_BOLD) {
                        dropped.setTag("");
                        dropped.setText("option");
                        dropped.setTypeface(Typeface.DEFAULT);
                    } else {
                        view.setVisibility(View.INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("choice drag listener", "ACTION_DRAG_ENDED");
                    if (!dragEvent.getResult()) {
                        final TextView droppedView = (TextView) dragEvent.getLocalState();
                        droppedView.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("choice drag listener", "dropped off view");
                                Object tag = droppedView.getTag();
                                if (tag != null && !tag.equals("") && droppedView.getTypeface() == Typeface.DEFAULT_BOLD) {
                                    Log.d("choice drag listener", "is a view to be dropped");
                                    int existingID = (Integer)tag;
                                    droppedView.setTag("");
                                    droppedView.setText("option");
                                    droppedView.setTypeface(Typeface.DEFAULT);
                                    getActivity().findViewById(existingID).setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    }
                    break;
            }
            return true;
        }
    }
}
