package com.teslyuk.androidtutorial_contentprovider.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.teslyuk.androidtutorial_contentprovider.model.LogModel;

import java.util.ArrayList;
import java.util.List;

public class LogDataBaseWrapper {

    public final static String AUTHORITY = "com.teslyuk.android.androidtutorial.db.log";
    public final static String TABLE_LOG = "LOG";

    public final static Uri LOG_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_LOG);

    public static final String TB_ID = "ID";

    //TABLE_LOG
    public static final String TB_MESSAGE = "MESSAGE";
    public static final String TB_EXCEPTION = "EXCEPTION";
    public static final String TB_DATETIME = "DATETIME";

    private ContentResolver mProvider;

    public LogDataBaseWrapper(Activity activity) {
        mProvider = activity.getContentResolver();
    }

    public List<LogModel> getLogs() {
        String[] projection = new String[]{TB_ID, TB_MESSAGE,
                TB_EXCEPTION, TB_DATETIME,};
        Cursor data = mProvider.query(LOG_URI, projection, null, null, null);
        List<LogModel> logItems = null;

        if (data != null && data.getCount() != 0) {
            logItems = new ArrayList<LogModel>();
            if (data.moveToFirst()) {
                do {
                    int id = data.getInt(data.getColumnIndex(TB_ID));
                    String message = data.getString(data.getColumnIndex(TB_MESSAGE));
                    String exception = data.getString(data.getColumnIndex(TB_EXCEPTION));
                    String datetime = data.getString(data.getColumnIndex(TB_DATETIME));

                    LogModel item = new LogModel();
                    item.setId(id);
                    item.setMessage(message);
                    item.setException(exception);
                    item.setDatetime(datetime);

                    logItems.add(item);
                } while (data.moveToNext());
            }
        }
        data.close();
        return logItems;
    }

    public List<LogModel> getLogsTime() {
        String[] projection = new String[]{TB_ID, TB_DATETIME,};
        Cursor data = mProvider.query(LOG_URI, projection, null, null, null);
        List<LogModel> logItems = null;

        if (data.getCount() != 0) {
            logItems = new ArrayList<LogModel>();
            if (data.moveToFirst()) {
                do {
                    int id = data.getInt(data.getColumnIndex(TB_ID));
                    String datetime = data.getString(data.getColumnIndex(TB_DATETIME));

                    LogModel item = new LogModel();
                    item.setId(id);
                    item.setDatetime(datetime);

                    logItems.add(item);
                } while (data.moveToNext());
            }
        }
        data.close();
        return logItems;
    }

    public void addLog(LogModel errorLog) {
        ContentValues values = new ContentValues();
        values.put(TB_MESSAGE, errorLog.getMessage());
        values.put(TB_EXCEPTION, errorLog.getException());
        values.put(TB_DATETIME, errorLog.getDatetime());

        mProvider.insert(LOG_URI, values);
    }

    public void cleanLogs() {
        mProvider.delete(LOG_URI, null, null);
    }

    public void cleanLogsByDate(int id) {
        String selection = TB_ID + " <= " + id;
        mProvider.delete(LOG_URI, selection, null);
    }

    public void removeLogById(int id) {
        String selection = TB_ID + " == " + id;
        mProvider.delete(LOG_URI, selection, null);
    }
}
