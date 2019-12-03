package com.jnu.i_time.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jnu.i_time.Day;
import com.jnu.i_time.R;

import java.util.ArrayList;
import java.util.List;

public class DayArrayAdapter extends ArrayAdapter<Day> {

    private int resourceId;

    public DayArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Day> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        View item = mInflater.inflate(this.resourceId, null);

        TextView interval = (TextView) item.findViewById(R.id.textView_interval);
        TextView date =(TextView) item.findViewById(R.id.textView_date);

        Day day_item = this.getItem(position);
        interval.setText(day_item.getInterval()+"DAYS");
        date.setText(day_item.getYear()+"年"+day_item.getMonth()+"月"+day_item.getDay()+"日");

        return item;
    }

}
