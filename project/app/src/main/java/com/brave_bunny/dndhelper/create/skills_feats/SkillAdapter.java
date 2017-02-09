package com.brave_bunny.dndhelper.create.skills_feats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesArmorUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesWeaponsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.addOrUpdateArmorSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.getArmorCount;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.addOrUpdateItemSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.getItemCount;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.addOrUpdateSkillSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.getSkillRanks;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.numberSkillPointsSpent;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.addOrUpdateWeaponSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.getWeaponCount;

/**
 * Created by Jemma on 1/30/2017.
 */

public class SkillAdapter extends CursorAdapter {

    private Context mContext;
    private View mRootView;
    private static int maximumSkillPoints;
    private static int skillRanksSpent;
    private static int maxRanks;
    private long mRowIndex;

    private int mType;
    public final static int TYPE_SKILLS = 0;
    public final static int TYPE_ARMOR = 1;
    public final static int TYPE_WEAPONS = 2;
    public final static int TYPE_ITEMS = 3;

    public SkillAdapter(Context context, Cursor c, int flags, long rowIndex, int type) {
        super(context, c, flags);
        mContext = context;
        mRowIndex = rowIndex;

        //TODO find currently selected skills
        skillRanksSpent = numberSkillPointsSpent(context, rowIndex);

        //TODO find maximum skill ranks to spend
        maximumSkillPoints = 13;

        //TODO change for cross-class
        maxRanks = 4;

        mType = type;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.list_item_skill, viewGroup, false);
        return mRootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final View rootView = view;

        ContentValues selectedItem = cursorRowToContentValues(cursor);

        long id;
        long itemIndex;
        int ranks;
        String itemName;
        ContentValues itemData;

        switch (mType) {
            case TYPE_SKILLS:
                itemName = RulesSkillsUtils.getSkillName(selectedItem);
                id = RulesSkillsUtils.getId(selectedItem);
                ranks = getSkillRanks(context, mRowIndex, id);
                break;
            case TYPE_ARMOR:
                itemName = RulesArmorUtils.getArmorName(selectedItem);
                id = RulesSkillsUtils.getId(selectedItem);
                ranks = getArmorCount(context, mRowIndex, id);
                break;
            case TYPE_WEAPONS:
                itemName = RulesWeaponsUtils.getWeaponName(selectedItem);
                id = RulesSkillsUtils.getId(selectedItem);
                ranks = getWeaponCount(context, mRowIndex, id);
                break;
            case TYPE_ITEMS:
                itemName = RulesItemsUtils.getItemName(selectedItem);
                id = RulesSkillsUtils.getId(selectedItem);
                ranks = getItemCount(context, mRowIndex, id);
                break;
            default:
                return;
        }

        TextView tvBody = (TextView) view.findViewById(R.id.skill_name);
        tvBody.setText(itemName);
        tvBody.setTag(R.string.skills, id);

        Button minusButton = (Button) view.findViewById(R.id.minus_button);
        minusButton.setEnabled(false);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementRanks(rootView);
            }
        });

        TextView rankText = (TextView) view.findViewById(R.id.skill_ranks);
        rankText.setText(Integer.toString(ranks));

        Button plusButton = (Button) view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementRanks(rootView);
            }
        });
    }

    private void decrementRanks(View rootView) {
        TextView tvBody = (TextView) rootView.findViewById(R.id.skill_name);
        TextView rankText = (TextView) rootView.findViewById(R.id.skill_ranks);
        Button minusButton = (Button) rootView.findViewById(R.id.minus_button);
        Button plusButton = (Button) rootView.findViewById(R.id.plus_button);

        int ranks = Integer.parseInt((String)rankText.getText());
        if (ranks == 0) return;

        ranks--;
        skillRanksSpent--;

        long skillId = (long)tvBody.getTag(R.string.skills);
        updateTable(skillId, ranks);

        if (ranks == 0) {
            minusButton.setEnabled(false);
        }
        plusButton.setEnabled(true);
        rankText.setText(Integer.toString(ranks));
    }

    private void incrementRanks(View rootView) {
        TextView tvBody = (TextView) rootView.findViewById(R.id.skill_name);
        TextView rankText = (TextView) rootView.findViewById(R.id.skill_ranks);
        Button minusButton = (Button) rootView.findViewById(R.id.minus_button);
        Button plusButton = (Button) rootView.findViewById(R.id.plus_button);

        int ranks = Integer.parseInt((String)rankText.getText());
        if (mType == TYPE_SKILLS) {
            if (skillRanksSpent == maximumSkillPoints) return;
            if (ranks == maxRanks) return;
        }

        ranks++;
        skillRanksSpent++;

        long skillId = (long)tvBody.getTag(R.string.skills);
        updateTable(skillId, ranks);

        if (mType == TYPE_SKILLS) {
            if (skillRanksSpent == maximumSkillPoints) {
                //TODO set all buttons disabled when no more skill points
                //plusButton.setEnabled(false);
            }
            if (ranks == maxRanks) {
                plusButton.setEnabled(false);
            }
        }
        minusButton.setEnabled(true);
        rankText.setText(Integer.toString(ranks));
    }

    private void updateTable(long skillId, int count) {
        switch(mType) {
            case TYPE_SKILLS:
                addOrUpdateSkillSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ARMOR:
                addOrUpdateArmorSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_WEAPONS:
                addOrUpdateWeaponSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ITEMS:
                addOrUpdateItemSelection(mContext, mRowIndex, skillId, count);
                break;
        }
    }

    //TODO
    private void updateSkillPointText() {
    }
}
