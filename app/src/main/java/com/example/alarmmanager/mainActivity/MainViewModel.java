package com.example.alarmmanager.mainActivity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarmmanager.database.AlarmRepository;
import com.example.alarmmanager.database.entities.Alarm;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Alarm>> mAlarmList;

    public AlarmRepository mAlarmrepository;

    public MainViewModel(Application mApplication) {
        super(mApplication);
        mAlarmrepository = new AlarmRepository(mApplication);
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return mAlarmrepository.getAllAlarms();
    }

    public void insertAlarm(){
        mAlarmrepository.insertAlarm(new Alarm(0,"0:00",5));
    }

    public void deleteAlarms(List<Alarm> deleteAlarmList){mAlarmrepository.deleteAlarms(deleteAlarmList); }


    /**
     * This class is needed to pass the application to the viewmodel
     */
    public static class MainViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;

        public MainViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MainViewModel(mApplication);
        }
    }
}
