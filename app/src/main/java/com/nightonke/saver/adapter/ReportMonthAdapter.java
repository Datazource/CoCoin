package com.nightonke.saver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nightonke.saver.R;
import com.nightonke.saver.util.CoCoinUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Weiping on 2016/2/2.
 */
public class ReportMonthAdapter extends BaseAdapter {

    private ArrayList<double[]> highestMonthExpense;
    private int year;

    public ReportMonthAdapter(ArrayList<double[]> highestMonthExpense, int year) {
        this.highestMonthExpense = highestMonthExpense;
        this.year = year;
    }

    @Override
    public int getCount() {
        return highestMonthExpense.size() - 2;
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

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_month, null);

        TextView icon = convertView.findViewById(R.id.month);
        TextView name = convertView.findViewById(R.id.month_name);
        TextView expense = convertView.findViewById(R.id.month_expense);
        TextView records = convertView.findViewById(R.id.month_sum);


        icon.setBackgroundResource(getBackgroundResource());
        icon.setText("" + ((int) highestMonthExpense.get(position + 1)[1] + 1));
        name.setText(CoCoinUtil.getMonthShort((int) highestMonthExpense.get(position + 1)[1] + 1)
                + " " + year + CoCoinUtil.getInstance().getPurePercentString(highestMonthExpense.get(position + 1)[4] * 100));
        expense.setText(CoCoinUtil.getInstance().getInMoney((int) highestMonthExpense.get(position + 1)[3]));
        records.setText(CoCoinUtil.getInstance().getInRecords((int) highestMonthExpense.get(position + 1)[5]));

        return convertView;
    }

    private int getBackgroundResource() {
        Random random = new Random();
        switch (random.nextInt(6)) {
            case 0:
                return R.drawable.bg_month_icon_small_0;
            case 1:
                return R.drawable.bg_month_icon_small_1;
            case 2:
                return R.drawable.bg_month_icon_small_2;
            case 3:
                return R.drawable.bg_month_icon_small_3;
            case 4:
                return R.drawable.bg_month_icon_small_4;
            case 5:
                return R.drawable.bg_month_icon_small_5;
            default:
                return R.drawable.bg_month_icon_small_0;
        }
    }

}
