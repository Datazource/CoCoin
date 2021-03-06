package com.nightonke.saver.ui;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.nightonke.saver.CoCoinApplication;
import com.nightonke.saver.R;
import com.nightonke.saver.fragment.CoCoinFragmentManager;

/**
 * Created by Weiping on 2016/1/23.
 */
public class CustomTitleSliderView extends BaseSliderView {
    private static Typeface font = null;
    private String content;
    private int type;
    private TextView title;

    public CustomTitleSliderView(String content, int type) {
        super(CoCoinApplication.getAppContext());
        this.content = content;
        this.type = type;
        if (type == CoCoinFragmentManager.NUMBER_SLIDER) {
            CoCoinFragmentManager.numberCustomTitleSliderView = this;
        } else if (type == CoCoinFragmentManager.EXPENSE_SLIDER) {
            CoCoinFragmentManager.expenseCustomTitleSliderView = this;
        }
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_title_slider_view, null);

        LinearLayout description = v.findViewById(R.id.description_layout);
        description.setVisibility(View.GONE);

        title = v.findViewById(R.id.title);
        title.setText(content);

        ImageView target = v.findViewById(R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }

    public void setTitle(String string) {
        title.setText(string);
    }
}
