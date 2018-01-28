package com.nightonke.saver.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.saver.R;
import com.nightonke.saver.CoCoinApplication;
import com.nightonke.saver.model.RecordManager;
import com.nightonke.saver.model.SettingManager;
import com.nightonke.saver.util.CoCoinUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by 伟平 on 2015/10/27.
 */

public class EditMoneyFragment extends Fragment {

    public MaterialEditText editView;
    public ImageView tagImage;
    public TextView tagName;
    Activity activity;
    private int fragmentPosition;
    private int tagId = -1;
    private View mView;

    static public EditMoneyFragment newInstance(int position, int type) {
        EditMoneyFragment fragment = new EditMoneyFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("type", type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.edit_money_fragment, container, false);

        if (getArguments().getInt("type") == CoCoinFragmentManager.MAIN_ACTIVITY_FRAGMENT) {
            CoCoinFragmentManager.mainActivityEditMoneyFragment = this;
        } else if (getArguments().getInt("type") == CoCoinFragmentManager.EDIT_RECORD_ACTIVITY_FRAGMENT) {
            CoCoinFragmentManager.editRecordActivityEditMoneyFragment = this;
        }

        fragmentPosition = getArguments().getInt("position");
        editView = mView.findViewById(R.id.money);
        tagImage = mView.findViewById(R.id.tag_image);
        tagName = mView.findViewById(R.id.tag_name);

//        editView.setTypeface(CoCoinUtil.typefaceLatoHairline);
        editView.setText("0");
        editView.requestFocus();
        editView.setHelperText(" ");
        editView.setKeyListener(null);
        editView.setOnClickListener(null);
        editView.setOnTouchListener(null);

        boolean shouldChange
                = SettingManager.getInstance().getIsMonthLimit()
                && SettingManager.getInstance().getIsColorRemind()
                && RecordManager.getCurrentMonthExpense()
                >= SettingManager.getInstance().getMonthWarning();

        setEditColor(shouldChange);

        if (getArguments().getInt("type") == CoCoinFragmentManager.EDIT_RECORD_ACTIVITY_FRAGMENT
                && CoCoinUtil.editRecordPosition != -1) {
            CoCoinFragmentManager.editRecordActivityEditMoneyFragment
                    .setTagImage(CoCoinUtil.getTagIcon(
                            RecordManager.SELECTED_RECORDS.get(CoCoinUtil.editRecordPosition).getTag()));
            CoCoinFragmentManager.editRecordActivityEditMoneyFragment
                    .setTagName(CoCoinUtil.getTagName(
                            RecordManager.SELECTED_RECORDS.get(CoCoinUtil.editRecordPosition).getTag()));
            CoCoinFragmentManager.editRecordActivityEditMoneyFragment
                    .setTagId(RecordManager.SELECTED_RECORDS.get(CoCoinUtil.editRecordPosition).getTag());
            CoCoinFragmentManager.editRecordActivityEditMoneyFragment
                    .setNumberText(String.format("%.0f", RecordManager.SELECTED_RECORDS.get(CoCoinUtil.editRecordPosition).getMoney()));
        }

        return mView;
    }

    public void updateTags() {

    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setTag(int p) {
        tagId = RecordManager.TAGS.get(p).getId();
        tagName.setText(CoCoinUtil.getTagName(RecordManager.TAGS.get(p).getId()));
        tagImage.setImageResource(CoCoinUtil.getTagIcon(RecordManager.TAGS.get(p).getId()));
    }

    public String getNumberText() {
        return editView.getText().toString();
    }

    public void setNumberText(String string) {
        editView.setText(string);
    }

    public String getHelpText() {
        return editView.getHelperText();
    }

    public void setHelpText(String string) {
        editView.setHelperText(string);
    }

    public void editRequestFocus() {
        editView.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                CoCoinApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }

    public void setEditColor(boolean shouldChange) {
        if (shouldChange) {
            editView.setTextColor(SettingManager.getInstance().getRemindColor());
            editView.setPrimaryColor(SettingManager.getInstance().getRemindColor());
            editView.setHelperTextColor(SettingManager.getInstance().getRemindColor());
        } else {
            editView.setTextColor(CoCoinUtil.getInstance().MY_BLUE);
            editView.setPrimaryColor(CoCoinUtil.getInstance().MY_BLUE);
            editView.setHelperTextColor(CoCoinUtil.getInstance().MY_BLUE);
        }
    }

    public void setTagName(String name) {
        tagName.setText(name);
    }

    public void setTagImage(int resource) {
        tagImage.setImageResource(resource);
    }

    public void getTagPosition(int[] position) {
        tagImage.getLocationOnScreen(position);
        position[0] += tagImage.getWidth() / 2;
        position[1] += tagImage.getHeight() / 2;
    }

    public interface OnTagItemSelectedListener {
        void onTagItemPicked(int position);
    }

}
