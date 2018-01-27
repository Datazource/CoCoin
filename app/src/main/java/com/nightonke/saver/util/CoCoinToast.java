package com.nightonke.saver.util;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.nightonke.saver.activity.CoCoinApplication;

/**
 * Created by Weiping on 2015/11/30.
 */
public class CoCoinToast {
    private static CoCoinToast ourInstance = new CoCoinToast();

    private CoCoinToast() {
    }

    public static CoCoinToast getInstance() {
        return ourInstance;
    }

    public void showToast(int text, int color) {
        showToast(CoCoinApplication.getAppContext().getResources().getString(text), color);
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(CoCoinApplication.getAppContext());
        superToast.setAnimations(CoCoinUtil.TOAST_ANIMATION);
        superToast.setDuration(Style.DURATION_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(Style.TEXTSIZE_SMALL);
        superToast.setText(text);
        superToast.setColor(color);
        superToast.setTypefaceStyle(Typeface.ITALIC);
        superToast.show();
    }
}
