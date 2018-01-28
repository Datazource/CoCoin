package com.nightonke.saver.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by 伟平 on 2015/10/20.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CoCoin Database.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_RECORDS = "Record";
    public static final String TABLE_TAGS = "Tag";
    public static final String TABLE_VIEW = "Record_Info";

    public interface RecordsColumn extends BaseColumns {
        public static final String MONEY = "Money";
        public static final String CURRENCY = "Currency";
        public static final String TAG_ID = "Tag_id";
        public static final String TIME = "Time";
        public static final String REMARK = "Remark";
        public static final String USER_ID = "User_id";
        public static final String OBJECT_ID = "Object_id";
        public static final String UPLOADED = "Is_uploaded";
    }

    public interface TagsColumn extends BaseColumns {
        public static final String NAME = "Name";
        public static final String WEIGHT = "Weight";
    }

    public static final String CREATE_RECORD_STRING =
            "create table Record (" +
                    "ID integer primary key autoincrement," +
                    "MONEY float," +
                    "CURRENCY text," +
                    "TAG integer," +
                    "TIME text," +
                    "REMARK text," +
                    "USER_ID text," +
                    "OBJECT_ID text," +
                    "IS_UPLOADED integer" + ")";

    public static final String CREATE_TAG_STRING =
            "create table Tag (" +
                    "ID integer primary key autoincrement," +
                    "NAME text," +
                    "WEIGHT integer)";

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECORDS + " ( "
                + RecordsColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RecordsColumn.MONEY + " FLOAT,"
                + RecordsColumn.CURRENCY + " TEXT,"
                + RecordsColumn.TAG_ID + " INTEGER,"
                + RecordsColumn.TIME + " TEXT,"
                + RecordsColumn.REMARK + " TEXT,"
                + RecordsColumn.USER_ID + " TEXT,"
                + RecordsColumn.OBJECT_ID + " TEXT,"
                + RecordsColumn.UPLOADED + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_TAGS + " ( "
                + TagsColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TagsColumn.NAME + " TEXT,"
                + TagsColumn.WEIGHT + " INTEGER)");

        db.execSQL("CREATE VIEW " + TABLE_VIEW + " AS SELECT * FROM " + TABLE_RECORDS + " LEFT OUTER JOIN "
                + TABLE_TAGS + " ON " + TABLE_RECORDS + "." + RecordsColumn.TAG_ID + "=" + TABLE_TAGS
                + "." + TagsColumn._ID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
