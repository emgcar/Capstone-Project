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
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesDomainsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterFeatsUtil.COL_CHARACTER_FEAT_FEAT_ID;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil.COL_CHARACTER_SPELL_SPELL_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesDomainsUtils.getDomain;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.COL_FEAT_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.getFeat;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.COL_SKILL_ID;
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

        ContentValues value;
        switch(listType) {
            case TYPE_SPELL:
                itemId = cursor.getLong(COL_CHARACTER_SPELL_SPELL_ID);
                value = getSpell(context, itemId);
                view.setTag(R.string.select_spells, cursor.getLong(COL_SPELL_ID));
                break;
            /*case TYPE_DOMAIN:
                itemId = cursor.getLong(COL_CHARACTER_DOMAIN_DOMAIN_ID);
                value = getDomain(context, itemId);
                view.setTag(R.string.select_spells, cursor.getLong(COL_DOMAIN_ID));
                break;
            case TYPE_SKILL:
                itemId = cursor.getLong(COL_CHARACTER_SKILL_SKILL_ID);
                value = getSkill(context, itemId);
                view.setTag(R.string.select_spells, cursor.getLong(COL_SKILL_ID));
                break;
            case TYPE_FEAT:
                itemId = cursor.getLong(COL_CHARACTER_FEAT_FEAT_ID);
                value = getFeat(context, itemId);
                view.setTag(R.string.select_spells, cursor.getLong(COL_FEAT_ID));
                break;*/
            default:
                return;
        }
        String name = value.getAsString(SPELL_COLUMNS[COL_SPELL_NAME]);
        nameText.setText(name);
    }
}
