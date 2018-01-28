package com.nightonke.saver.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nightonke.saver.BuildConfig;
import com.nightonke.saver.db.DatabaseOpenHelper.RecordsColumn;
import com.nightonke.saver.db.DatabaseOpenHelper.TagsColumn;
import com.nightonke.saver.model.CoCoinRecord;
import com.nightonke.saver.model.RecordManager;
import com.nightonke.saver.model.Tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Created by 伟平 on 2015/10/20.
 */

public class DBHelper {
    private static DBHelper sDBHelper;
    private SQLiteDatabase sqliteDatabase;

    private DBHelper(Context context) throws IOException {
        sqliteDatabase = new DatabaseOpenHelper(context).getWritableDatabase();
    }

    public synchronized static DBHelper getInstance(Context context)
            throws IOException {
        if (sDBHelper == null) {
            sDBHelper = new DBHelper(context);
        }
        return sDBHelper;
    }

    public void getData() {
        RecordManager.RECORDS = new LinkedList<>();
        RecordManager.TAGS = new LinkedList<>();

        Cursor cursor = sqliteDatabase.query(DatabaseOpenHelper.TABLE_TAGS, null, null,
                null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Tag tag = new Tag();
                    tag.setId(cursor.getInt(cursor.getColumnIndex(TagsColumn._ID)) - 1);
                    tag.setName(cursor.getString(cursor.getColumnIndex(TagsColumn.NAME)));
                    tag.setWeight(cursor.getInt(cursor.getColumnIndex(TagsColumn.WEIGHT)));
                    RecordManager.TAGS.add(tag);
                }
            }
            cursor.close();
        }

        cursor = sqliteDatabase.query(DatabaseOpenHelper.TABLE_RECORDS, null, null,
                null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    CoCoinRecord coCoinRecord = new CoCoinRecord();
                    coCoinRecord.setId(cursor.getLong(cursor.getColumnIndex(RecordsColumn._ID)));
                    coCoinRecord.setMoney(cursor.getFloat(cursor.getColumnIndex(RecordsColumn.MONEY)));
                    coCoinRecord.setCurrency(cursor.getString(cursor.getColumnIndex(RecordsColumn.CURRENCY)));
                    coCoinRecord.setTag(cursor.getInt(cursor.getColumnIndex(RecordsColumn.TAG_ID)));
                    coCoinRecord.setCalendar(cursor.getString(cursor.getColumnIndex(RecordsColumn.TIME)));
                    coCoinRecord.setRemark(cursor.getString(cursor.getColumnIndex(RecordsColumn.REMARK)));
                    coCoinRecord.setUserId(cursor.getString(cursor.getColumnIndex(RecordsColumn.USER_ID)));
                    coCoinRecord.setLocalObjectId(cursor.getString(cursor.getColumnIndex(RecordsColumn.OBJECT_ID)));
                    coCoinRecord.setIsUploaded(cursor.getInt(cursor.getColumnIndex(RecordsColumn.UPLOADED)) != 0);

                    if (BuildConfig.DEBUG) {
                        Log.d("CoCoin Debugger", "Load " + coCoinRecord.toString() + " S");
                    }
                    RecordManager.RECORDS.add(coCoinRecord);
                    RecordManager.SUM += (int) coCoinRecord.getMoney();
                }
            }
            cursor.close();
        }
    }

    /**
     * @param coCoinRecord
     * @return return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long saveRecord(CoCoinRecord coCoinRecord) {
        ContentValues values = new ContentValues();
        values.put(RecordsColumn.MONEY, coCoinRecord.getMoney());
        values.put(RecordsColumn.CURRENCY, coCoinRecord.getCurrency());
        values.put(RecordsColumn.TAG_ID, coCoinRecord.getTag());
        values.put(RecordsColumn.TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(coCoinRecord.getCalendar().getTime()));
        values.put(RecordsColumn.REMARK, coCoinRecord.getRemark());
        values.put(RecordsColumn.USER_ID, coCoinRecord.getUserId());
        values.put(RecordsColumn.OBJECT_ID, coCoinRecord.getLocalObjectId());
        values.put(RecordsColumn.UPLOADED, coCoinRecord.getIsUploaded().equals(Boolean.FALSE) ? 0 : 1);
        long insertId = sqliteDatabase.insert(DatabaseOpenHelper.TABLE_RECORDS, null, values);
        coCoinRecord.setId(insertId);
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.saveRecord " + coCoinRecord.toString() + " S");
        }
        return insertId;
    }

    /**
     * @param tag
     * @return return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public int saveTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(TagsColumn.NAME, tag.getName());
        values.put(TagsColumn.WEIGHT, tag.getWeight());
        int insertId = (int) sqliteDatabase.insert(DatabaseOpenHelper.TABLE_TAGS, null, values);
        tag.setId(insertId);
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.saveTag " + tag.toString() + " S");
        }
        return insertId - 1;
    }

    /**
     * @param id
     * @return return the id of the record deleted
     */
    public long deleteRecord(long id) {
        long deletedNumber = sqliteDatabase.delete(DatabaseOpenHelper.TABLE_RECORDS,
                RecordsColumn._ID + " = ?",
                new String[]{id + ""});
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.deleteRecord id = " + id + " S");
            Log.d("CoCoin Debugger", "sDBHelper.deleteRecord number = " + deletedNumber + " S");
        }
        return id;
    }

    /**
     * @param id
     * @return return the id of the tag deleted
     */
    public int deleteTag(int id) {
        int deletedNumber = sqliteDatabase.delete(DatabaseOpenHelper.TABLE_TAGS,
                TagsColumn._ID + " = ?",
                new String[]{(id + 1) + ""});
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.deleteTag id = " + id + " S");
            Log.d("CoCoin Debugger", "sDBHelper.deleteTag number = " + deletedNumber + " S");
        }
        return id;
    }

    /**
     * @param coCoinRecord
     * @return return the id of the coCoinRecord update
     */
    public long updateRecord(CoCoinRecord coCoinRecord) {
        ContentValues values = new ContentValues();
        values.put(RecordsColumn.MONEY, coCoinRecord.getMoney());
        values.put(RecordsColumn.CURRENCY, coCoinRecord.getCurrency());
        values.put(RecordsColumn.TAG_ID, coCoinRecord.getTag());
        values.put(RecordsColumn.TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(coCoinRecord.getCalendar().getTime()));
        values.put(RecordsColumn.REMARK, coCoinRecord.getRemark());
        values.put(RecordsColumn.USER_ID, coCoinRecord.getUserId());
        values.put(RecordsColumn.OBJECT_ID, coCoinRecord.getLocalObjectId());
        values.put(RecordsColumn.UPLOADED, coCoinRecord.getIsUploaded().equals(Boolean.FALSE) ? 0 : 1);
        sqliteDatabase.update(DatabaseOpenHelper.TABLE_RECORDS, values,
                RecordsColumn._ID + " = ?",
                new String[]{coCoinRecord.getId() + ""});
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.updateRecord " + coCoinRecord.toString() + " S");
        }
        return coCoinRecord.getId();
    }

    /**
     * @param tag
     * @return return the id of the tag update
     */
    public int updateTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(TagsColumn.NAME, tag.getName());
        values.put(TagsColumn.WEIGHT, tag.getWeight());
        sqliteDatabase.update(DatabaseOpenHelper.TABLE_TAGS, values,
                TagsColumn._ID + " = ?",
                new String[]{(tag.getId() + 1) + ""});
        if (BuildConfig.DEBUG) {
            Log.d("CoCoin Debugger", "sDBHelper.updateTag " + tag.toString() + " S");
        }
        return tag.getId();
    }

    /**
     * delete all the records
     *
     * @return
     */
    public int deleteAllRecords() {
        int deleteNum = sqliteDatabase.delete(DatabaseOpenHelper.TABLE_RECORDS, null, null);
        Log.d("CoCoin Debugger", "sDBHelper.deleteAllRecords " + deleteNum + " S");
        return deleteNum;
    }
}
