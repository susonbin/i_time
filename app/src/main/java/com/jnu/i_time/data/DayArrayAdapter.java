package com.jnu.i_time.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jnu.i_time.MainActivity;
import com.jnu.i_time.data.Day;
import com.jnu.i_time.R;

import java.util.ArrayList;

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
        TextView meaasge=(TextView)item.findViewById(R.id.textView_message);
        TextView date =(TextView) item.findViewById(R.id.textView_date);
        TextView tail=(TextView) item.findViewById(R.id.textView_tail);

        Day day_item = this.getItem(position);
        interval.setText(day_item.getInterval()+"  DAYS");
        interval.setBackground(MainActivity.getContext().getResources().getDrawable(day_item.getPictureId()));
        meaasge.setText("  "+day_item.getMessage());
        date.setText("   "+day_item.getYear()+"年"+day_item.getMonth()+"月"+day_item.getDay()+"日");
        tail.setBackground(MainActivity.getContext().getResources().getDrawable(day_item.getPictureId()));

        return item;
    }

}
