package com.example.alarmmanager.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;

@Database(entities = {Alarm.class, AlarmStep.class}, version = 6, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {




    abstract public  AlarmDao alarmDao();

    private static AlarmDatabase INSTANCE;

    public static AlarmDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, "alarm_manager_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AlarmDao mDao;
        Alarm[] alarms = {
                new Alarm(5,"6:06",133430),
                new Alarm(1,"2:40",20),
                new Alarm(2,"0:50",30),
                new Alarm(3,"3:45",40),
                new Alarm(4,"6:06",10),
                new Alarm(6,"2:40",20),
                new Alarm(7,"0:50",30),
                new Alarm(8,"3:45",40)};
        AlarmStep[] alarmSteps = {
                new AlarmStep(0,1,60,"Laufen",0),
                new AlarmStep(0,1,133430,"Ruhe",1),
                new AlarmStep(0,1,80,"Plank",2),
                new AlarmStep(0,1,130,"Ruhe",3),
                new AlarmStep(0,2,60,"Plank",0),
                new AlarmStep(0,2,130,"Ruhe",1),

        };



        PopulateDbAsync(AlarmDatabase db) {
            mDao = db.alarmDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAlarmTable();
            mDao.deleteAlarmStepTable();

            for (int i = 0; i <= alarms.length - 1; i++) {
                Alarm alarm = alarms[i];
                mDao.insertAlarm(alarm);
            }

            for (int i = 0; i <= alarmSteps.length - 1; i++) {
                AlarmStep alarmStep = alarmSteps[i];
                mDao.insertAlarmStep(alarmStep);
            }

            return null;
        }
    }
}

