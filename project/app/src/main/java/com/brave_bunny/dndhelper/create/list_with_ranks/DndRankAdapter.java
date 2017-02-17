package com.brave_bunny.dndhelper.create.list_with_ranks;

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
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesArmorUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesWeaponsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesArmorUtils.getArmorCost;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils.getItemCost;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesWeaponsUtils.getWeaponCost;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.addOrUpdateArmorSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.getArmorCount;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.setCharacterMoneyAndUpdateTable;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.addOrUpdateItemSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.getItemCount;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.addOrUpdateSkillSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.getSkillRanks;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.numberSkillPointsSpent;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.addOrUpdateWeaponSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.getWeaponCount;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ARMOR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_WEAPON;

/**
 * Created by Jemma on 1/30/2017.
 */

public class DndRankAdapter extends CursorAdapter {

    private Context mContext;
    private View mRootView;
    private TextView mSummaryText;
    private static int maximumSkillPoints;
    private static int skillRanksSpent;
    private static int maxRanks;
    private long mRowIndex;

    private int mType;

    private static float moneyAvailable;

    public DndRankAdapter(Context context, Cursor c, int flags, long rowIndex, int type, TextView pointsText) {
        super(context, c, flags);
        mContext = context;
        mRowIndex = rowIndex;

        skillRanksSpent = numberSkillPointsSpent(context, rowIndex);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(mContext, mRowIndex);
        maximumSkillPoints = InProgressSkillsUtil.getTotalSkillPointsToSpend(values);

        //TODO change for cross-class
        maxRanks = RulesSkillsUtils.maxRanksPerLevel(1);

        mType = type;

        moneyAvailable = InProgressCharacterUtil.getCharacterMoney(mContext, mRowIndex);

        mSummaryText = pointsText;
        updateSummaryText();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.list_item_dnd_ranks, viewGroup, false);
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
            case TYPE_SKILL:
                itemName = RulesSkillsUtils.getSkillName(selectedItem);
                id = RulesSkillsUtils.getSkillId(selectedItem);
                ranks = getSkillRanks(context, mRowIndex, id);
                break;
            case TYPE_ARMOR:
                itemName = RulesArmorUtils.getArmorName(selectedItem);
                id = RulesSkillsUtils.getSkillId(selectedItem);
                ranks = getArmorCount(context, mRowIndex, id);
                break;
            case TYPE_WEAPON:
                itemName = RulesWeaponsUtils.getWeaponName(selectedItem);
                id = RulesSkillsUtils.getSkillId(selectedItem);
                ranks = getWeaponCount(context, mRowIndex, id);
                break;
            case TYPE_ITEM:
                itemName = RulesItemsUtils.getItemName(selectedItem);
                id = RulesSkillsUtils.getSkillId(selectedItem);
                ranks = getItemCount(context, mRowIndex, id);
                break;
            default:
                return;
        }

        TextView tvBody = (TextView) view.findViewById(R.id.skill_name);
        tvBody.setText(itemName);
        tvBody.setTag(R.string.skills, id);

        Button minusButton = (Button) view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementRanks(rootView);
            }
        });

        Button plusButton = (Button) view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementRanks(rootView);
            }
        });

        TextView rankText = (TextView) view.findViewById(R.id.skill_ranks);
        rankText.setText(Integer.toString(ranks));
        if (ranks == 0) {
            minusButton.setEnabled(false);
        } else {
            minusButton.setEnabled(true);
        }
        switch (mType) {
            case TYPE_SKILL:
                if ((skillRanksSpent == maximumSkillPoints) || (ranks == maxRanks)) {
                    plusButton.setEnabled(false);
                } else {
                    plusButton.setEnabled(true);
                }
                break;
            default:
                if (getCost(id) > moneyAvailable) {
                    plusButton.setEnabled(false);
                } else {
                    plusButton.setEnabled(true);
                }
        }
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
        if (mType != TYPE_SKILL) {
            unbuyItem(skillId);
        }
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
        long skillId = (long)tvBody.getTag(R.string.skills);

        int ranks = Integer.parseInt((String)rankText.getText());
        if (mType == TYPE_SKILL) {
            if (skillRanksSpent == maximumSkillPoints) return;
            if (ranks == maxRanks) return;
        } else {
            if (getCost(skillId) > moneyAvailable) return;
        }

        ranks++;
        skillRanksSpent++;

        if (mType != TYPE_SKILL) {
            buyItem(skillId);
        }
        updateTable(skillId, ranks);

        if (mType == TYPE_SKILL) {
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
            case TYPE_SKILL:
                addOrUpdateSkillSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ARMOR:
                addOrUpdateArmorSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_WEAPON:
                addOrUpdateWeaponSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ITEM:
                addOrUpdateItemSelection(mContext, mRowIndex, skillId, count);
                break;
        }
        updateSummaryText();
    }

    private void buyItem(long itemId) {
        moneyAvailable -= getCost(itemId);
        setCharacterMoneyAndUpdateTable(mContext, mRowIndex, moneyAvailable);
    }

    private void unbuyItem(long itemId) {
        moneyAvailable += getCost(itemId);
        setCharacterMoneyAndUpdateTable(mContext, mRowIndex, moneyAvailable);
    }

    private float getCost(long itemId) {
        switch(mType) {
            case TYPE_ARMOR:
                return getArmorCost(mContext, itemId);
            case TYPE_WEAPON:
                return getWeaponCost(mContext, itemId);
            case TYPE_ITEM:
                return getItemCost(mContext, itemId);
        }
        return 0;
    }

    //TODO
    private void updateSummaryText() {
        if (mType == TYPE_SKILL) {
            mSummaryText.setText(mContext.getString(R.string.skill_points_left, maximumSkillPoints-skillRanksSpent));
        } else {
            mSummaryText.setText(mContext.getString(R.string.money_left, moneyAvailable));
        }
    }
}
