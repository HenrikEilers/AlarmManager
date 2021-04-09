package com.example.alarmmanager.mainActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.alarmmanager.R;
import com.example.alarmmanager.database.entities.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemTouchListener {

    private MainViewModel mainViewModel;

    private RecyclerView mRecyclerView;
    private  AlarmRecyclerViewAdapter mAdapter;

    private List<Alarm> mAlarmList;

    private boolean onDelete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewModel
        mainViewModel =
                new ViewModelProvider(this, new MainViewModel.MainViewModelFactory(this.getApplication())).get(MainViewModel.class);

        mAlarmList = new ArrayList<Alarm>();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onDelete) {
                    ArrayList<Alarm> deleteList = new ArrayList<Alarm>();
                    for (int i = 0; i < mAlarmList.size(); i++) {
                        if (mAlarmList.get(i).isChecked() == true) {
                            deleteList.add(mAlarmList.get(i));
                        }
                    }
                    //exitDeleteMode();
                    mainViewModel.deleteAlarms(deleteList);
                }else{
                    mainViewModel.insertAlarm();
                }
            }
        });

        //RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new AlarmRecyclerViewAdapter(this, mAlarmList.toArray(new Alarm[0]),this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //ViewModel observer for RecyclerView Data
        mainViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> s) {
                mAlarmList = s;
                if(s.size()==0&&onDelete){exitDeleteMode();}
                mAdapter.setAlarms(mAlarmList.toArray(new Alarm[0]));
            }
        });

        //OnBackPressed Callback
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(onDelete==true){
                    exitDeleteMode();
                }
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //is called when the settings icon on a item of the RecyclerView is clicked
    @Override
    public void onSettingsCLick(int position) {
        //TODO:SettingsActivity Here
    }

    //is called when a item of the RecylerView is held
    @Override
    public void onHoldItem(int position){
        enterDeleteMode();
    }

    //Is called when one item of the RecyclerView is clicked
    @Override
    public void onTouchItem(int position) {
        if(this.onDelete==true){
            mAlarmList.get(position).setChecked(!mAlarmList.get(position).isChecked());
            mAdapter.setChecked(position,mAlarmList.get(position));
        }else{
            //TODO:AlarmActivity Here
        }
    }

    //Is called when a checkbox in RecyclerView changes
    @Override
    public void onCheckChange(int position, boolean isChecked) {
        mAlarmList.get(position).setChecked(isChecked);
        mAdapter.setChecked(position,mAlarmList.get(position));
    }

    //Sets all Attributest for the Deletion Design
    private void enterDeleteMode(){
        this.onDelete= true;
        this.mAdapter.setOnDelete(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        fab.setImageResource(R.drawable.ic_baseline_delete_24);
        fab.setColorFilter(Color.WHITE);
    }

    //Resets all Attributs to normal mode and resets all checkboxes
    private  void exitDeleteMode(){
        onDelete= false;
        for(int i = 0;i<mAlarmList.size();i++) {
            mAlarmList.get(i).setChecked(false);
        }
        mAdapter.setOnDeleteReset(mAlarmList.toArray(new Alarm[0]));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200,null)));
        fab.setImageResource(R.drawable.ic_baseline_add_24);
        fab.setColorFilter(Color.BLACK);
    }
}