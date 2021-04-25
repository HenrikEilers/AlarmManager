package com.example.alarmmanager.startAlarmActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmmanager.R;
import com.example.alarmmanager.database.entities.Alarm;
import com.example.alarmmanager.database.entities.AlarmStep;
import com.example.alarmmanager.mainActivity.AlarmRecyclerViewAdapter;
import com.example.alarmmanager.mainActivity.OnItemTouchListener;

public class AlarmStepRecyclerViewAdapter extends RecyclerView.Adapter<AlarmStepRecyclerViewAdapter.AlarmStepViewHolder> {
    private AlarmStep[] mAlarmSteps;

    private LayoutInflater mInflater;
    private Context context;

    public AlarmStepRecyclerViewAdapter(Context context, AlarmStep[] mAlarmSteps) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mAlarmSteps = mAlarmSteps;
    }

    @NonNull
    @Override
    public AlarmStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.alarm_step_list_item,
                parent, false);
        return new AlarmStepRecyclerViewAdapter.AlarmStepViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmStepViewHolder holder, int position) {
        AlarmStep mCurrent =  this.mAlarmSteps[position];
        holder.alarmStepTitleTextView.setText(mCurrent.getTitle());
        holder.alarmStepDurationTextView.setText(Alarm.parseDurationIntToString(mCurrent.getDuration()));

    }

    @Override
    public int getItemCount() {
        return this.mAlarmSteps.length;
    }
    public void setAlarmSteps(AlarmStep[] alarmSteps){
        this.mAlarmSteps = alarmSteps;
        super.notifyDataSetChanged();
    }

    static class AlarmStepViewHolder extends RecyclerView.ViewHolder {
        public final TextView alarmStepTitleTextView;
        public final TextView alarmStepDurationTextView;


        final AlarmStepRecyclerViewAdapter mAdapter;

        public AlarmStepViewHolder(View itemView, AlarmStepRecyclerViewAdapter adapter) {
            super(itemView);
            alarmStepTitleTextView = itemView.findViewById(R.id.alarm_step_title_textview);
            alarmStepDurationTextView = itemView.findViewById(R.id.alarm_step_duration_textview);
            this.mAdapter = adapter;
        }
    }
}
