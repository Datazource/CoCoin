package com.nightonke.saver.ui;

/**
 * Created by 伟平 on 2015/11/16.
 */

public interface RiseNumberBase {
    void start();

    RiseNumberTextView withNumber(float number);

    RiseNumberTextView withNumber(int number);

    RiseNumberTextView setDuration(long duration);

    void setOnEnd(RiseNumberTextView.EndListener callback);
}
