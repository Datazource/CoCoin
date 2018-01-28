package com.nightonke.saver.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.nightonke.saver.CoCoinApplication;
import com.nightonke.saver.R;

/**
 * Created by hushujun on 2018/1/28.
 */

public class CustomerFontTextView extends AppCompatTextView {
    private static Typeface sHairline = Typeface.createFromAsset(CoCoinApplication.getAppContext().getAssets(),
            "fonts/Lato-Hairline.ttf");
    private static Typeface sRegular = Typeface.createFromAsset(CoCoinApplication.getAppContext().getAssets(),
            "fonts/Lato-Regular.ttf");
    private static Typeface sLight = Typeface.createFromAsset(CoCoinApplication.getAppContext().getAssets(),
            "fonts/LatoLatin-Light.ttf");

    public static final int TYPE_HAIRLINE = 0;
    public static final int TYPE_REGULAR = 1;
    public static final int TYPE_LIGHT = 2;

    public CustomerFontTextView(Context context) {
        this(context, null);
    }

    public CustomerFontTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int type = TYPE_LIGHT;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomerFontTextView);
            type = ta.getInt(R.styleable.CustomerFontTextView_fontType, TYPE_LIGHT);
            ta.recycle();
        }
        setFontType(type);
    }

    public void setFontType(int type) {
        switch (type) {
            case TYPE_HAIRLINE:
                setTypeface(sHairline);
                break;
            case TYPE_REGULAR:
                setTypeface(sRegular);
                break;
            case TYPE_LIGHT:
                setTypeface(sLight);
                break;
            default:
                break;
        }
    }
}
