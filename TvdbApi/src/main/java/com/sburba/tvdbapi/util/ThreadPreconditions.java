package com.sburba.tvdbapi.util;

import android.os.Looper;

import com.sburba.tvdbapi.BuildConfig;

public class ThreadPreconditions {
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method must be called from the UI thread");
            }
        }
    }
}
