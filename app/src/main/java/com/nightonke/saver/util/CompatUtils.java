package com.nightonke.saver.util;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by hushujun on 2018/1/28.
 */

public class CompatUtils {
    /**
     * @param context
     * @param color
     * @return
     */
    @ColorInt
    public static int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }
}
