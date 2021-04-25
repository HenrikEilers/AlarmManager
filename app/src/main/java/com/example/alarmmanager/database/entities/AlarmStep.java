package com.example.alarmmanager.database.entities;


import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

@Entity(tableName = "alarmstep_table")
public class AlarmStep {
    @PrimaryKey(autoGenerate = true)
    public int alarmStepID;
    public int alarm_step_owner_id;
    public int duration;
    public String title;
    public int reihenfolge;

    public AlarmStep(int alarmStepID,int alarm_step_owner_id, int duration, String title,int reihenfolge) {
        this.alarmStepID = alarmStepID;
        this.alarm_step_owner_id = alarm_step_owner_id;
        this.duration = duration;
        this.title = title;
        this.reihenfolge = reihenfolge;
    }

    public int getAlarmStepID() {
        return alarmStepID;
    }

    public void setAlarmStepID(int alarmStepID) {
        this.alarmStepID = alarmStepID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
