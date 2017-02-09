package com.brave_bunny.dndhelper.play;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterFeatsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSkillsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.getFeat;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.COL_SKILL_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.COL_SKILL_NAME;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.SKILL_COLUMNS;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.getSkill;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.COL_SPELL_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.COL_SPELL_NAME;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.SPELL_COLUMNS;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.getSpell;

/**
 * Created by Jemma on 2/7/2017.
 */

public class UseAbilityListAdapter extends CursorAdapter {

    public static final int TYPE_SPELL = 0;
    public static final int TYPE_DOMAIN = 1;
    public static final int TYPE_SKILL = 2;
    public static final int TYPE_FEAT = 3;


    View mView;
    private Context mContext;
    private long mRowIndex;
    private int listType;

    public UseAbilityListAdapter(Context context, Cursor c, int flags, int type, long rowIndex) {
        super(context, c, flags);
        mContext = context;
        listType = type;
        mRowIndex = rowIndex;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int layoutId = R.layout.list_item_domain;
        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameText = (TextView) view.findViewById(R.id.name_details);
        long itemId;

        ContentValues selectedItem = cursorRowToContentValues(cursor);
        ContentValues itemData;

        String name;
        switch(listType) {
            case TYPE_SPELL:
                itemId = CharacterSpellsUtil.getSpellId(selectedItem);
                itemData = getSpell(context, itemId);
                name = RulesSpellsUtils.getSpellName(itemData);
                break;
            /*case TYPE_DOMAIN:
                itemId = CharacterClassUtil.getDomainId(selectedItem);
                itemData = getDomain(context, itemId);
                name = RulesDomainUtils.getDomainName(itemData);
                break;*/
            case TYPE_SKILL:
                itemId = CharacterSkillsUtil.getSkillId(selectedItem);
                itemData = getSkill(context, itemId);
                name = RulesSkillsUtils.getSkillName(itemData);
                break;
            case TYPE_FEAT:
                itemId = CharacterFeatsUtil.getFeatId(selectedItem);
                itemData = getFeat(context, itemId);
                name = RulesFeatsUtils.getFeatName(itemData);
                break;
            default:
                return;
        }
        nameText.setText(name);
        view.setTag(R.string.select_spells, itemId);
    }
}
