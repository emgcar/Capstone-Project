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

/**
 * Created by Jemma on 1/18/2017.
 */

public class SelectionListAdapter extends CursorAdapter {
    View mView;
    private int numberSelected;
    private int maxSelected;

    public SelectionListAdapter(Context context, Cursor c, int flags, int maxAllowed) {
        super(context, c, flags);
        numberSelected = 0;
        maxSelected = maxAllowed;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_domain;
        mView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mView.setEnabled(false);
        return mView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(RulesUtils.COL_DOMAIN_NAME);
        TextView nameText = (TextView) view.findViewById(R.id.name_details);
        nameText.setText(name);
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