package com.nightonke.saver.util;

/**
 * Created by 伟平 on 2015/11/12.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nightonke.saver.R;

class ExpandableItemIndicatorImplNoAnim extends ExpandableItemIndicator.Impl {
    private ImageView mImageView;

    @Override
    public void onInit(Context context, AttributeSet attrs,
                       int defStyleAttr, ExpandableItemIndicator thiz) {
        View v = LayoutInflater.from(context).
                inflate(R.layout.widget_expandable_item_indicator_no_anim, thiz, true);
        mImageView = v.findViewById(R.id.image_view);
    }

    @Override
    public void setExpandedState(boolean isExpanded, boolean animate) {
        int resId = (isExpanded) ? R.mipmap.ic_expand_less : R.mipmap.ic_expand_more;
        mImageView.setImageResource(resId);
    }
}