package com.example.alarmmanager.startAlarmActivity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarmmanager.database.AlarmRepository;
import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;
import com.example.alarmmanager.database.relations.AlarmWithAlarmSteps;
import com.example.alarmmanager.mainActivity.MainViewModel;

import java.util.List;

public class StartAlarmViewModel extends AndroidViewModel {
    public LiveData<List<AlarmStep>> mAlarmList;

    public AlarmRepository mAlarmrepository;

    public StartAlarmViewModel(Application mApplication) {
        super(mApplication);
        mAlarmrepository = new AlarmRepository(mApplication);
    }

    public LiveData<AlarmWithAlarmSteps> getAlarmWithSteps(int alarm_id) {
        return mAlarmrepository.getAlarmWithSteps(alarm_id);
    }

    /**
     * This class is needed to pass the application to the viewmodel
     */
    public static class StartAlarmViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;

        public StartAlarmViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new StartAlarmViewModel(mApplication);
        }
    }
}
