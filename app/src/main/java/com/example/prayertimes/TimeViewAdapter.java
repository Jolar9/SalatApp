package com.example.prayertimes;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeViewAdapter extends RecyclerView.Adapter<TimeViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<PrayerTime> list;

    public TimeViewAdapter(Context c,ArrayList<PrayerTime> l){
        context = c;
        list = l;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.time_view,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.name.setText(list.get(i).getName());
        holder.time.setText(list.get(i).getTime());

        boolean test = list.get(i).isNext();

        if(test){ //for test
            holder.layout.setBackgroundResource(R.drawable.now_prayer);
            holder.icon.setBackgroundResource(R.drawable.ic_baseline_notifications_none_24);

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,time;
        LinearLayout layout;
        TextView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.prayerName);
            time = (TextView) itemView.findViewById(R.id.prayerTime);
            layout = itemView.findViewById(R.id.timeView);
            icon = (TextView) itemView.findViewById(R.id.notifyIcon);

        }
    }

}
