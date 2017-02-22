package cstu.handypharmacy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cstu.handypharmacy.R;
import cstu.handypharmacy.model.District;

public class DistrictAdapter extends ArrayAdapter<District> {

    private Context mContext;
    private int mLayoutResId;
    private ArrayList<District> mList;

    // constructor
    public DistrictAdapter(Context context, int resource, ArrayList<District> list) {
        super(context, resource, list);
        mContext = context;
        mLayoutResId = resource;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemLayout = View.inflate(mContext, mLayoutResId, null);
        //TextView provId = (TextView) itemLayout.findViewById(R.id.provId);
        TextView provName = (TextView) itemLayout.findViewById(R.id.provName);

        final District district = mList.get(position);

        //provId.setText(province.getProId());
        provName.setText(district.getDisName());

        return itemLayout;

        //return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(final int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setPadding(5, 5, 5, 5);
        label.setText(mList.get(position).getDisName());
        return label;
        //return super.getDropDownView(position, convertView, parent);
    }


}
