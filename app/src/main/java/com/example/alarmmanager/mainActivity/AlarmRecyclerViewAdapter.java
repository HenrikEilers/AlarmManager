package com.example.alarmmanager.mainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmmanager.R;
import com.example.alarmmanager.database.entities.Alarm;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder>{

    private Alarm[] mAlarms;

    private LayoutInflater mInflater;
    private Context context;
    private  OnItemTouchListener onItemTouchListener;

    private boolean onDelete;



    public AlarmRecyclerViewAdapter(Context context, Alarm[] mAlarmList, OnItemTouchListener onItemTouchListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mAlarms = mAlarmList;
        this.onItemTouchListener = onItemTouchListener;
        this.onDelete = false;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.alarm_list_item,
                parent, false);
        return new AlarmRecyclerViewAdapter.AlarmViewHolder(mItemView, this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm mCurrent =  mAlarms[position];
        holder.textView3.setText(mCurrent.getName());
        HoldGestureListener holdGestureListener = new HoldGestureListener(position,onItemTouchListener);
        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(context,holdGestureListener);
        holder.imageview.setOnClickListener(holdGestureListener);
        holder.constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                v.callOnClick();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setPressed(false);
                        break;
                }
                return true;
            }
        });

        //CheckBox configuration
        if(this.onDelete==false){
            holder.checkBox.setVisibility(View.GONE);
            if(holder.checkBox.isChecked()!=false){
                holder.checkBox.setChecked(false);
            }
        }else{
            if(holder.checkBox.isChecked()!=mCurrent.isChecked()){
                holder.checkBox.setChecked(mCurrent.isChecked());
            }
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onItemTouchListener.onCheckChange(position,isChecked);
            }
        });
    }


    public void onViewRecycled(AlarmViewHolder holder){
        //setOnCheckedChangeListener would be called after recycling with out ofBounds position(index)
        holder.checkBox.setOnCheckedChangeListener(null);
    }

    @Override
    public int getItemCount() {
        return this.mAlarms.length;
    }

    //Sets Data for RecyclerView
    public void setAlarms(Alarm[] alarms){
        this.mAlarms = alarms;
        super.notifyDataSetChanged();
    }

    //Sets CheckboxStatus for one specific item
    public void setChecked(int position, Alarm alarm){
        this.mAlarms[position] = alarm;
        super.notifyItemChanged(position);
    }

    //Sets OnDelete to false and resets the CheckBoyStatuses of all Items
    public void setOnDeleteReset(Alarm[] alarms){
        this.onDelete = false;
        this.mAlarms = alarms;
        super.notifyDataSetChanged();
    }

    //Sets OnDelete
    public void setOnDelete(boolean onDelete){
        this.onDelete = onDelete;
        super.notifyDataSetChanged();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView3;
        public final ImageView imageview;
        public final CardView cardView;
        public final ConstraintLayout constraintLayout;

        final AlarmRecyclerViewAdapter mAdapter;
        private final CheckBox checkBox;

        public AlarmViewHolder(View itemView, AlarmRecyclerViewAdapter adapter) {
            super(itemView);
            textView3 = itemView.findViewById(R.id.textView3);
            imageview = itemView.findViewById(R.id.settings_image_view);
            cardView = itemView.findViewById(R.id.cardView);
            checkBox = itemView.findViewById(R.id.checkbox);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            this.mAdapter = adapter;
        }
    }
}
