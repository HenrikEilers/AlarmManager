package com.example.alarmmanager.database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarmstep_table")
public class AlarmStep {
    @PrimaryKey
    public int alarmStepID;
    public int duration;
}
