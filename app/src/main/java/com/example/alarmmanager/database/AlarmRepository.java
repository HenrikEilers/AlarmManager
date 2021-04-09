package com.example.alarmmanager.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.alarmmanager.database.entities.Alarm;

import java.util.List;

public class AlarmRepository {

    //Dao
    private AlarmDao mAlarmDao;

    //LiveData Variables
    private LiveData<List<Alarm>> mAllAlarms;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        mAlarmDao = db.alarmDao();

    }

    public LiveData<List<Alarm>> getAllAlarms() {
        mAllAlarms = mAlarmDao.getAllAlarms();
        return mAllAlarms;
    }

    public void deleteAlarms(List<Alarm> alarmList){new deleteAlarmsAsyncTask(mAlarmDao).execute(alarmList);}

    public void insertAlarm(Alarm alarm) {
        new insertAlarmAsyncTask(mAlarmDao).execute(alarm);
    }


    private static class insertAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private final AlarmDao mAsyncTaskDao;

        insertAlarmAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            mAsyncTaskDao.insertAlarm(params[0]);
            return null;
        }
    }

    private static class deleteAlarmsAsyncTask extends AsyncTask<List<Alarm>, Void, Void> {

        private final AlarmDao mAsyncTaskDao;

        deleteAlarmsAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Alarm>... params) {
            mAsyncTaskDao.deleteAlarms(params[0]);
            return null;
        }
    }
}
