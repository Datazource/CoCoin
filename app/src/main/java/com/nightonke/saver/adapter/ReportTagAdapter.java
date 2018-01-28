package com.nightonke.saver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.saver.R;
import com.nightonke.saver.fragment.ReportViewFragment;
import com.nightonke.saver.util.CoCoinUtil;

import java.util.ArrayList;

/**
 * Created by Weiping on 2016/1/30.
 */
public class ReportTagAdapter extends BaseAdapter {

    private ArrayList<double[]> tagExpense;

    public ReportTagAdapter(ArrayList<double[]> tagExpense) {
        this.tagExpense = tagExpense;
    }

    @Override
    public int getCount() {
        return min(tagExpense.size() - 1, ReportViewFragment.MAX_TAG_EXPENSE);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_tag, null);

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.tag_name);
        TextView expense = convertView.findViewById(R.id.tag_expense);
        TextView records = convertView.findViewById(R.id.tag_sum);


        icon.setImageDrawable(CoCoinUtil.getInstance().getTagIconDrawable((int) tagExpense.get(position + 1)[2]));
        name.setText(CoCoinUtil.getInstance().getTagName((int) tagExpense.get(position + 1)[2])
                + CoCoinUtil.getInstance().getPurePercentString(tagExpense.get(position + 1)[1] * 100));
        expense.setText(CoCoinUtil.getInstance().getInMoney((int) tagExpense.get(position + 1)[0]));
        records.setText(CoCoinUtil.getInstance().getInRecords((int) tagExpense.get(position + 1)[3]));

        return convertView;
    }

    private int min(int a, int b) {
        return (a < b ? a : b);
    }
}
