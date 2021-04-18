package com.app.weblib;

import android.util.Log;

public interface TouchListener  {
    default void onSwipeLeft() {
        Log.d( "rrrr","Swipe left");
    }
    default void onSwipeRight() {
        Log.d( "rrrr","Swipe right");
    }
}