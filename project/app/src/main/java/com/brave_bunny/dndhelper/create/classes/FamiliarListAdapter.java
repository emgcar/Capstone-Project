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
 * Created by Jemma on 1/18/2017.
 */

public class FamiliarListAdapter extends CursorAdapter {
    View mView;
    private Context mContext;
    private long mRowIndex;

    public FamiliarListAdapter(Context context, Cursor c, int flags, long rowIndex) {
        super(context, c, flags);
        mContext = context;
        mRowIndex = rowIndex;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_domain;
        mView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        return mView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(RulesUtils.COL_FAMILIAR_NAME);
        TextView nameText = (TextView) view.findViewById(R.id.name_details);
        nameText.setText(name);
        view.setTag(R.string.select_familiar, cursor.getLong(RulesUtils.COL_FAMILIAR_ID));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setEnabled(false);
        long familiarIndex = (long)view.getTag(R.string.select_familiar);
        if (InProgressUtil.isFamiliarSelected(mContext, mRowIndex, familiarIndex)) {
            view.setEnabled(true);
        }
        return view;
    }
}