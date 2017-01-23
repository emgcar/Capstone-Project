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

public class DeityListAdapter extends CursorAdapter {
    View mView;
    private int numberSelected;
    private int maxSelected;
    private Context mContext;
    private long mRowIndex;

    public DeityListAdapter(Context context, Cursor c, int flags, long rowIndex, int maxAllowed) {
        super(context, c, flags);
        numberSelected = 0;
        maxSelected = maxAllowed;
        mContext = context;
        mRowIndex = rowIndex;
    }

    //TODO: edge case with domains selected that aren't allowed in current alignment
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_domain;
        mView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        numberSelected = InProgressUtil.getNumberDomainsSelected(context, mRowIndex);
        return mView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(RulesUtils.COL_DOMAIN_NAME);
        TextView nameText = (TextView) view.findViewById(R.id.name_details);
        nameText.setText(name);
        view.setTag(R.string.select_domains, cursor.getLong(RulesUtils.COL_DOMAIN_ID));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setEnabled(false);
        long domainIndex = (long)view.getTag(R.string.select_domains);
        if (InProgressUtil.isDomainSelected(mContext, mRowIndex, domainIndex)) {
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
}
