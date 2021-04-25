package com.example.alarmmanager.database.entities;

import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public int alarm_id;

    public String name;
    public int dauer;

    @Ignore
    public boolean isChecked;


    public Alarm(int alarm_id, String name, int dauer) {
        this.alarm_id = alarm_id;
        this.name = name;
        this.dauer = dauer;
        isChecked=false;
    }

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @SuppressLint("DefaultLocale")
    public static String parseDurationIntToString(int totalSec){
        int seconds = totalSec % 60;
        int minutes = totalSec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                String daySingularOrPlural = (days==1)?"Tag":"Tage";
                return String.format("%d %s %02d:%02d:%02d", days,daySingularOrPlural,hours%24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%02d:%02d", minutes, seconds);
    }

}
