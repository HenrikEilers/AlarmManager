package com.example.alarmmanager.startAlarmActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alarmmanager.R;
import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;
import com.example.alarmmanager.database.relations.AlarmWithAlarmSteps;
import com.example.alarmmanager.mainActivity.AlarmRecyclerViewAdapter;
import com.example.alarmmanager.mainActivity.MainActivity;
import com.example.alarmmanager.mainActivity.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class StartAlarmActivity extends AppCompatActivity {
    private  AlarmStepRecyclerViewAdapter mAdapter;

    private AlarmWithAlarmSteps mAlarmWithAlarmSteps;

    private  StartAlarmViewModel mStartAlarmViewModel;
    private TextView nameDurationTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_alarm);
        mAlarmWithAlarmSteps = new AlarmWithAlarmSteps();

        //ViewModel
        mStartAlarmViewModel =
                new ViewModelProvider(this, new StartAlarmViewModel.StartAlarmViewModelFactory(this.getApplication())).get(StartAlarmViewModel.class);


        //Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //TextView
        nameDurationTextView = findViewById(R.id.name_duration_textview);

        //RecyclerView
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new AlarmStepRecyclerViewAdapter(this, mAlarmWithAlarmSteps.getAlarmSteps().toArray(new AlarmStep[0]));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int alarm_id = getIntent().getIntExtra(MainActivity.ALARM_ID,0);

        //ViewModel observer for RecyclerView Data
        mStartAlarmViewModel.getAlarmWithSteps(alarm_id).observe(this, new Observer<AlarmWithAlarmSteps>() {
            @Override
            public void onChanged(@Nullable AlarmWithAlarmSteps alarmWithAlarmSteps) {
                mAlarmWithAlarmSteps = alarmWithAlarmSteps;
                mAdapter.setAlarmSteps(mAlarmWithAlarmSteps.getAlarmSteps().toArray(new AlarmStep[0]));
                String nameAndDuration = mAlarmWithAlarmSteps.getAlarm().getName()+" - "+Alarm.parseDurationIntToString(mAlarmWithAlarmSteps.getAlarm().getDauer())+" - "+mAlarmWithAlarmSteps.getAlarm().getAlarm_id();
                nameDurationTextView.setText(nameAndDuration);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_alarm, menu);
        return true;
    }

    //Is called when a menu Item or Icon is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}