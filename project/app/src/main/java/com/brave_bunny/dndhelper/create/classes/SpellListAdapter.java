package com.brave_bunny.dndhelper.create.classes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

/**
 * Created by Jemma on 1/23/2017.
 */

public class SpellListAdapter extends CursorAdapter {
    View mView;
    private int numberSelected;
    private int maxSelected;
    private Context mContext;
    private long mRowIndex;

    public SpellListAdapter(Context context, Cursor c, int flags, long rowIndex, int maxAllowed) {
        super(context, c, flags);
        numberSelected = 0;
        maxSelected = maxAllowed;
        mContext = context;
        mRowIndex = rowIndex;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_domain;
        mView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        numberSelected = InProgressUtil.getNumberSpellSelected(context, mRowIndex);
        return mView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(RulesUtils.COL_SPELL_NAME);
        TextView nameText = (TextView) view.findViewById(R.id.name_details);
        nameText.setText(name);
        view.setTag(R.string.select_spells, cursor.getLong(RulesUtils.COL_SPELL_ID));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setEnabled(false);
        long spellIndex = (long)view.getTag(R.string.select_spells);
        if (InProgressUtil.isSpellSelected(mContext, mRowIndex, spellIndex)) {
            view.setEnabled(true);
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

    public void changeMaxSelected(int newMax) {
        maxSelected = newMax;
    }
}
