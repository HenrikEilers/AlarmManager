package com.example.alarmmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;
import com.example.alarmmanager.database.relations.AlarmWithAlarmSteps;

import java.util.List;

@Dao
public interface AlarmDao {


    @Query("DELETE FROM alarm_table")
    void deleteAlarmTable();

    @Query("DELETE FROM alarmstep_table")
    void deleteAlarmStepTable();

    @Delete
    void deleteAlarms(List<Alarm> alarmList);

    @Insert
    void insertAlarm(Alarm alarm);

    @Insert
    void insertAlarmStep(AlarmStep alarmStep);

    @Query("SELECT * from alarm_table ORDER BY alarm_id ASC")
   LiveData< List<Alarm>> getAllAlarms();

    @Transaction
    @Query("SELECT * FROM alarm_table WHERE alarm_id = :alarm_id")
    public LiveData<AlarmWithAlarmSteps> getAlarmWithSteps(int alarm_id);
}
