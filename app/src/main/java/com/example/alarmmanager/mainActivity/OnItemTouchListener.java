package com.example.alarmmanager.mainActivity;


public interface OnItemTouchListener {

    public void onSettingsCLick(int position);

    public void onHoldItem(int position);

    public void onTouchItem(int position);

    public void onCheckChange(int position, boolean isChecked);
}
