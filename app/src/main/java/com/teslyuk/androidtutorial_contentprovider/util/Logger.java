package com.teslyuk.androidtutorial_contentprovider.util;

import android.util.Log;

/**
 * Created by taras on 25.11.15.
 */
public class Logger {

    public static void i(String TAG, String message) {
        Log.i(TAG, message);
    }

    public static void e(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static void d(String TAG, String message) {
        Log.d(TAG, message);
    }
}
