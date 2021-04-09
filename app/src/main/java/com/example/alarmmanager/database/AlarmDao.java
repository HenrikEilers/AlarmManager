package com.example.alarmmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alarmmanager.database.entities.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {


    @Query("DELETE FROM alarm_table")
    void deleteAlarmTable();

    @Delete
    void deleteAlarms(List<Alarm> alarmList);

    @Insert
    void insertAlarm(Alarm alarm);

    @Query("SELECT * from alarm_table ORDER BY alarm_id ASC")
   LiveData< List<Alarm>> getAllAlarms();
}
