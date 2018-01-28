package com.nightonke.saver.util;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nightonke.saver.BuildConfig;
import com.nightonke.saver.CoCoinApplication;
import com.nightonke.saver.R;
import com.nightonke.saver.db.DatabaseOpenHelper;
import com.nightonke.saver.model.CoCoinRecord;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

//import com.github.johnpersano.supertoasts.library.Style;
//import com.github.johnpersano.supertoasts.library.SuperToast;
//import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

/**
 * Created by 伟平 on 2015/10/16.
 */

// Todo make CoCoinUtil singleton
// Todo floating labels in english

public class CoCoinUtil {

    private static final int[] EMPTY_STATE = new int[]{};
    public static int editRecordPosition = -1;
    public static String LOGO_PATH = "/sdcard/logo/";
    public static String LOGO_NAME = "logo.jpg";
//    public static int TOAST_ANIMATION = Style.ANIMATIONS_FLY;
    public static int MY_BLUE;
    public static CoCoinRecord backupCoCoinRecord;
    public static String PASSWORD = "1234";

    public static boolean WEEK_START_WITH_SUNDAY = false;
    public static RelativeSizeSpan relativeSizeSpan;
    public static ForegroundColorSpan redForegroundSpan;
    public static ForegroundColorSpan greenForegroundSpan;
    public static ForegroundColorSpan whiteForegroundSpan;
    public static Double INPUT_MIN_EXPENSE = 0d;
    public static Double INPUT_MAX_EXPENSE = 99999d;
    private static String lastColor0, lastColor1, lastColor2;
    private static Random random;

    private static String lastToast = "";
    private static CoCoinUtil ourInstance = new CoCoinUtil();

    private CoCoinUtil() {
    }

    public static void init(Context context) {

        relativeSizeSpan = new RelativeSizeSpan(2f);
        redForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ff5252"));
        greenForegroundSpan = new ForegroundColorSpan(Color.parseColor("#4ca550"));
        whiteForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ffffff"));

        lastColor0 = "";
        lastColor1 = "";
        lastColor2 = "";

        random = new Random();

        MY_BLUE = ContextCompat.getColor(CoCoinApplication.getAppContext(), R.color.my_blue);
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getWhetherBlank() {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return "";
        } else {
            return " ";
        }
    }

    public static String getWhetherFuck() {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return "日";
        } else {
            return "";
        }
    }

    public static String getInMoney(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return "¥" + money;
        } else {
            return "$" + money + " ";
        }
    }

    public static String getInRecords(int records) {
        return records + "'s";
    }

    public static String getSpendString(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return "消费 ¥" + money;
        } else {
            return "Spend $" + money + " ";
        }
    }

    public static String getSpendString(double money) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return "消费 ¥" + (int) money;
        } else {
            return "Spend $" + (int) money + " ";
        }
    }

    public static String getPercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return " (占" + String.format("%.2f", percent) + "%)";
        } else {
            return " (takes " + String.format("%.2f", percent) + "%)";
        }
    }

    public static String getPurePercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return " " + String.format("%.2f", percent) + "%";
        } else {
            return " " + String.format("%.2f", percent) + "%";
        }
    }

    public static String getTodayViewTitle(int fragmentPosition) {
        return CoCoinApplication.getAppContext().getString(Constances.TODAY_VIEW_TITLE[fragmentPosition]);
    }

    public static String getAxisDateName(int type, int position) {
        switch (type) {
            case Calendar.HOUR_OF_DAY:
                return position + "";
            case Calendar.DAY_OF_WEEK:
                if (WEEK_START_WITH_SUNDAY) {
                    return CoCoinApplication.getAppContext().getResources()
                            .getString(Constances.WEEKDAY_SHORT_START_ON_SUNDAY[position + 1]);
                } else {
                    return CoCoinApplication.getAppContext().getResources()
                            .getString(Constances.WEEKDAY_SHORT_START_ON_MONDAY[position + 1]);
                }
            case Calendar.DAY_OF_MONTH:
                return (position + 1) + "";
            case Calendar.MONTH:
                return CoCoinApplication.getAppContext().getResources()
                        .getString(Constances.MONTHS_SHORT[position + 1]);
            default:
                return "";
        }
    }

    public static int getTodayViewEmptyTip(int fragmentPosition) {
        return Constances.TODAY_VIEW_EMPTY_TIP[fragmentPosition];
    }

    public static String getMonthShort(int i) {
        return CoCoinApplication.getAppContext().getResources().getString(Constances.MONTHS_SHORT[i]);
    }

    public static String getMonth(int i) {
        return CoCoinApplication.getAppContext().getResources().getString(Constances.MONTHS[i]);
    }

    public static String getWeekDay(int position) {
        if (WEEK_START_WITH_SUNDAY) {
            return CoCoinApplication.getAppContext().getResources()
                    .getString(Constances.WEEKDAY_START_ON_SUNDAY[position + 1]);
        } else {
            return CoCoinApplication.getAppContext().getResources()
                    .getString(Constances.WEEKDAY_START_ON_MONDAY[position + 1]);
        }
    }

    public static Calendar getTodayLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getTodayRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getYesterdayLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getYesterdayRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getThisWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoCoinUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        }
        return calendar;
    }

    public static Calendar getThisWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) getThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar getLastWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoCoinUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        }
        return calendar;
    }

    public static Calendar getLastWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) getLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar getNextWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoCoinUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        }
        return calendar;
    }

    public static Calendar getNextWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) getNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar getNextWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) getNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar getThisWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) getThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar getLastWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) getLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar getThisMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getThisMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getLastMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getLastMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getThisYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getThisYearRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getLastYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getLastYearRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static boolean clickButtonDelete(int position) {
        return position == 9;
    }

    public static boolean clickButtonCommit(int position) {
        return position == 11;
    }

    public static boolean clickButtonIsZero(int position) {
        return position == 10;
    }

    public static double toDollas(double money, String currency) {

        return 1.0 * money;

    }

    public static boolean isStringRelation(String s1, String s2) {
        return true;
    }

    public static int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Constances.Colors.length);
        while (Constances.Colors[p].equals(lastColor0)
                || Constances.Colors[p].equals(lastColor1)
                || Constances.Colors[p].equals(lastColor2)) {
            p = random.nextInt(Constances.Colors.length);
        }
        lastColor0 = lastColor1;
        lastColor1 = lastColor2;
        lastColor2 = Constances.Colors[p];
        return Color.parseColor(Constances.Colors[p]);
    }

    public static int getTagColorResource(int tag) {
        return Constances.TAG_COLOR[tag + 2];
    }

    public static int getTagColor(int tag) {
        return ContextCompat.getColor(CoCoinApplication.getAppContext(), Constances.TAG_COLOR[tag + 3]);
    }

    public static Drawable getTagDrawable(int tagId) {
        return ContextCompat.getDrawable(
                CoCoinApplication.getAppContext(), Constances.TAG_DRAWABLE[tagId + 3]);
    }

    public static String getTagUrl(int tagId) {
        return Constances.TAG_HEADER_URL[tagId + 3];
    }

    public static int getSnackBarBackground(int tagId) {
        return Constances.TAG_SNACK[tagId + 3];
    }

    public static int getTagIcon(int tagId) {
        return Constances.TAG_ICON[tagId + 2];
    }

    public static Drawable getTagIconDrawable(int tagId) {
        return ContextCompat.getDrawable(
                CoCoinApplication.getAppContext(), Constances.TAG_ICON[tagId + 2]);
    }

    public static String getTagName(int tagId) {
        return CoCoinApplication.getAppContext().getResources().getString(Constances.TAG_NAME[tagId + 2]);
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortTreeMapByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            @Override
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }

    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = CoCoinApplication.getAppContext()
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = CoCoinApplication.getAppContext()
                    .getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getDeeperColor(int color) {
        int alpha = Color.alpha(color);
        int red = (int) (Color.red(color) * 0.8);
        int green = (int) (Color.green(color) * 0.8);
        int blue = (int) (Color.blue(color) * 0.8);
        return Color.argb(alpha, red, green, blue);
    }

    public static int getAlphaColor(int color) {
        int alpha = 6;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static void getKeyBoard(MaterialEditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void getKeyBoard(EditText editText) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                CoCoinApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = CoCoinApplication.getAppContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int getToolBarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;
    }

    public static HashMap<String, Integer> getDrawerTopUrl() {
        HashMap<String, Integer> drawerTopUrls = new HashMap<>();
        drawerTopUrls.put("0", Constances.DRAWER_TOP_URL[0]);
        drawerTopUrls.put("1", Constances.DRAWER_TOP_URL[1]);
        drawerTopUrls.put("2", Constances.DRAWER_TOP_URL[2]);
        drawerTopUrls.put("3", Constances.DRAWER_TOP_URL[3]);
        drawerTopUrls.put("4", Constances.DRAWER_TOP_URL[4]);
        return drawerTopUrls;
    }

    public static HashMap<String, Integer> getTransparentUrls() {
        HashMap<String, Integer> transparentUrls = new HashMap<>();
        transparentUrls.put("0", R.mipmap.transparent);
        transparentUrls.put("1", R.mipmap.transparent);
        return transparentUrls;
    }

//    public static void showToast(Context context, String text, int color) {
//        SuperToast.cancelAllSuperToasts();
//        SuperToast superToast = new SuperToast(context);
//        superToast.setAnimations(Style.ANIMATIONS_FADE);
//        superToast.setDuration(Style.DURATION_SHORT);
//        superToast.setTextColor(Color.parseColor("#ffffff"));
//        superToast.setTextSize(Style.TEXTSIZE_SMALL);
//        superToast.setText(text);
//        superToast.setColor(color);
//        superToast.show();
//    }

//    public static void showToast(Context context, String text) {
//        if (context == null) {
//            return;
//        }
//        if (lastToast.equals(text)) {
//            SuperToast.cancelAllSuperToasts();
//        } else {
//            lastToast = text;
//        }
//        SuperToast superToast = new SuperToast(context);
//        superToast.setAnimations(Style.ANIMATIONS_FADE);
//        superToast.setDuration(Style.DURATION_VERY_SHORT);
//        superToast.setTextColor(Color.parseColor("#ffffff"));
//        superToast.setTextSize(Style.TEXTSIZE_SMALL);
//        superToast.setText(text);
//        superToast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE));
//        superToast.show();
//    }

//    public static void showToast(Context context, int textId) {
//        String text = context.getResources().getString(textId);
//        if (context == null) {
//            return;
//        }
//        if (lastToast.equals(text)) {
//            SuperToast.cancelAllSuperToasts();
//        } else {
//            lastToast = text;
//        }
//        SuperToast superToast = new SuperToast(context);
//        superToast.setAnimations(Style.ANIMATIONS_FADE);
//        superToast.setDuration(Style.DURATION_VERY_SHORT);
//        superToast.setTextColor(Color.parseColor("#ffffff"));
//        superToast.setTextSize(Style.TEXTSIZE_SMALL);
//        superToast.setText(text);
//        superToast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE));
//        superToast.show();
//    }

    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    public static int getScreenWidth(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static Point getScreenSize(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static String getCurrentVersion() {
        return "CoCoin V" + CoCoinApplication.VERSION / 100 + "." + CoCoinApplication.VERSION % 100 / 10
                + "." + CoCoinApplication.VERSION % 10;
    }

    public static String getString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static String getCalendarString(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        return (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.YEAR);
    }

    public static String getCalendarStringRecordCheckDialog(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + calendar.get(Calendar.DAY_OF_MONTH) + getWhetherFuck() + " " + calendar.get(Calendar.YEAR);
        }
        return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + " "
                + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static String getCalendarStringDayExpenseSort(Context context, int year, int month, int day) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[month]) + " " + day + " " + year;
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[month]) + day + getWhetherFuck() + " " + year;
        }
        return context.getResources().getString(Constances.MONTHS_SHORT[month]) + " " + day + " " + year;
    }

    public static String getCalendarString(Context context, String string) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(string));
        } catch (ParseException p) {

        }
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(Constances.MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1])
                    + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        return (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.YEAR);
    }

    public static String getRecordDatabasePath(Context context) {
        String databasePath = "";
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            databasePath = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            databasePath = "/data/data/" + context.getPackageName() + "/databases/";
        }
        databasePath += DatabaseOpenHelper.DB_NAME;
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin", "Get record database path " + databasePath);
        }
        return databasePath;
    }

    // if the uploaded file's size and name is the same, the BmobProFile.upload will not upload in fact
    public static void deleteBmobUploadCach(Context context) {
//        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(context, "bmob", null, 1);
//        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
//        sqliteDatabase.delete("upload", "_id>?", new String[]{"0"});
//        String databasePath = "";
//        if (android.os.Build.VERSION.SDK_INT >= 17) {
//            databasePath = context.getApplicationInfo().dataDir + "/databases/";
//        } else {
//            databasePath = "/data/data/" + context.getPackageName() + "/databases/";
//        }
//        databasePath += "bmob";
//        File file = new File(databasePath);
//        if (file.exists()) file.delete();
    }

    // the tagId is clothes, food, house and traffic
    public static int isCFHT(int tagId) {
        if (tagId == 2) {
            return 0;
        } else if (tagId == -3 || tagId == -2 || tagId == -1 || tagId == 0 || tagId == 15 || tagId == 19 || tagId == 20) {
            return 1;
        } else if (tagId == 3 || tagId == 24) {
            return 2;
        } else if (tagId == 4 || tagId == 5) {
            return 3;
        }
        return -1;
    }

    public static int textCounter(String s) {
        int counter = 0;
        for (char c : s.toCharArray()) {
            if (c < 128) {
                counter++;
            } else {
                counter += 2;
            }
        }
        return counter;
    }

    public static void copyToClipboard(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public static CoCoinUtil getInstance() {
        if (ourInstance == null) {
            ourInstance = new CoCoinUtil();
            init(CoCoinApplication.getAppContext());
        }
        return ourInstance;
    }

    public static class MyShakeAnimator extends BaseViewAnimator {
        private int amplitude;

        public MyShakeAnimator() {
            amplitude = 25;
        }

        public MyShakeAnimator(int amplitude) {
            this.amplitude = amplitude;
        }

        @Override
        public void prepare(View target) {
            int amplitude1 = (int) (amplitude * 0.4);
            int amplitude2 = (int) (amplitude * 0.2);
            getAnimatorAgent().playTogether(
                    ObjectAnimator.ofFloat(target, "translationX", 0, amplitude, -amplitude,
                            amplitude, -amplitude, amplitude, -amplitude, amplitude, -amplitude,
                            amplitude, -amplitude, amplitude1, -amplitude1, amplitude2, -amplitude2,
                            0)
            );
        }
    }
}
