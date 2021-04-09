package com.example.alarmmanager.mainActivity;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class HoldGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnClickListener {
    private int position;
    private  OnItemTouchListener onItemTouchListener;

    public HoldGestureListener(int position, OnItemTouchListener onItemTouchListener) {
        this.position = position;
        this.onItemTouchListener = onItemTouchListener;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onItemTouchListener.onTouchItem(position);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        onItemTouchListener.onHoldItem(position);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onClick(View v) {
        onItemTouchListener.onSettingsCLick(position);
    }
}
