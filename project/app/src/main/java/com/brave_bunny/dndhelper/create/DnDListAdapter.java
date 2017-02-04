package com.brave_bunny.dndhelper.create;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesDomainsUtils.COL_DOMAIN_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesDomainsUtils.COL_DOMAIN_NAME;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFamiliarsUtils.COL_FAMILIAR_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFamiliarsUtils.COL_FAMILIAR_NAME;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.COL_FEAT_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.COL_FEAT_NAME;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.COL_SPELL_ID;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.COL_SPELL_NAME;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.getNumberDomainsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.isDomainSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.getNumberFeatsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.isFeatSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.getNumberSpellSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.isSpellSelected;

/**
 * Created by Jemma on 1/30/2017.
 */

//TODO double click causes problems
public class DnDListAdapter extends CursorAdapter {
    public static final int LIST_TYPE_DEITY = 0;
    public static final int LIST_TYPE_FAMILIAR = 1;
    public static final int LIST_TYPE_SPELL = 2;
    public static final int LIST_TYPE_FEAT = 3;


    View mView;
    private int numberSelected;
    private int maxSelected;
    private Context mContext;
    private long mRowIndex;
    private int listType;

    public DnDListAdapter(Context context, Cursor c, int flags, int type, long rowIndex, int maxAllowed) {
        super(context, c, flags);
        mContext = context;
        listType = type;
        mRowIndex = rowIndex;
        maxSelected = maxAllowed;
    }

    //TODO: edge case with domains selected that aren't allowed in current alignment
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_domain;
        mView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);

        switch (listType) {
            case LIST_TYPE_DEITY:
                numberSelected = getNumberDomainsSelected(context, mRowIndex);
                break;
            case LIST_TYPE_FAMILIAR:
                break;
            case LIST_TYPE_SPELL:
                numberSelected = getNumberSpellSelected(context, mRowIndex);
                break;
            case LIST_TYPE_FEAT:
                numberSelected = getNumberFeatsSelected(context, mRowIndex);
                break;
        }
        return mView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = "";
        TextView nameText = (TextView) view.findViewById(R.id.name_details);

        switch (listType) {
            case LIST_TYPE_DEITY:
                name = cursor.getString(COL_DOMAIN_NAME);
                view.setTag(R.string.select_domains, cursor.getLong(COL_DOMAIN_ID));
                break;
            case LIST_TYPE_FAMILIAR:
                name = cursor.getString(COL_FAMILIAR_NAME);
                view.setTag(R.string.select_familiar, cursor.getLong(COL_FAMILIAR_ID));
                break;
            case LIST_TYPE_SPELL:
                name = cursor.getString(COL_SPELL_NAME);
                view.setTag(R.string.select_spells, cursor.getLong(COL_SPELL_ID));
                break;
            case LIST_TYPE_FEAT:
                name = cursor.getString(COL_FEAT_NAME);
                view.setTag(R.string.select_feats, cursor.getLong(COL_FEAT_ID));
                break;
        }

        nameText.setText(name);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setEnabled(false);

        long index;
        switch (listType) {
            case LIST_TYPE_DEITY:
                index = (long)view.getTag(R.string.select_domains);
                if (isDomainSelected(mContext, mRowIndex, index)) {
                    view.setEnabled(true);
                }
                break;
            case LIST_TYPE_FAMILIAR:
                index = (long)view.getTag(R.string.select_familiar);
                if (InProgressCharacterUtil.isFamiliarSameAsSelected(mContext, mRowIndex, index)) {
                    view.setEnabled(true);
                }
                break;
            case LIST_TYPE_SPELL:
                index = (long)view.getTag(R.string.select_spells);
                if (isSpellSelected(mContext, mRowIndex, index)) {
                    view.setEnabled(true);
                }
                break;
            case LIST_TYPE_FEAT:
                index = (long)view.getTag(R.string.select_feats);
                if (isFeatSelected(mContext, mRowIndex, index)) {
                    view.setEnabled(true);
                }
                break;
        }
        return view;
    }

    public int getNumberSelected() {
        return numberSelected;
    }

    public void decreaseNumberSelected() {
        numberSelected--;
    }

    public void increaseNumberSelected() {
        numberSelected++;
    }

    public boolean atMaxSelected() {
        return (numberSelected == maxSelected);
    }
}
