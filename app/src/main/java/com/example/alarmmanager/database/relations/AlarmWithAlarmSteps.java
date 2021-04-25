package com.example.alarmmanager.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;

import java.util.ArrayList;
import java.util.List;

public class AlarmWithAlarmSteps {


    @Embedded public Alarm alarm;
    @Relation(
            parentColumn = "alarm_id",
            entityColumn = "alarm_step_owner_id"
    )
    public List<AlarmStep> alarmSteps;

    public AlarmWithAlarmSteps(Alarm alarm, List<AlarmStep> alarmSteps) {
        this.alarm = alarm;
        this.alarmSteps = alarmSteps;
    }
    public AlarmWithAlarmSteps() {
        this.alarm = new Alarm(-1,"",0);
        this.alarmSteps = new ArrayList<AlarmStep>();
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public List<AlarmStep> getAlarmSteps() {
        return alarmSteps;
    }

    public void setAlarmSteps(List<AlarmStep> alarmSteps) {
        this.alarmSteps = alarmSteps;
    }
}
