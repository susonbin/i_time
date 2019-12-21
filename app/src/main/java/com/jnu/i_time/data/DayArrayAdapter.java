package com.jnu.i_time.data;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.jnu.i_time.R;

import java.util.ArrayList;
import java.util.Date;

public class DayArrayAdapter extends ArrayAdapter<Day> {

    private int resourceId;

    public DayArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Day> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        View item = mInflater.inflate(this.resourceId, null);

        TextView interval = (TextView) item.findViewById(R.id.textView_interval);
        TextView name=(TextView)item.findViewById(R.id.textView_name);
        TextView date =(TextView) item.findViewById(R.id.textView_date);
        TextView tail=(TextView) item.findViewById(R.id.textView_tail);

        Day day_item = this.getItem(position);
        String sub=String.format("%tY年%<tm月%<td日 %<tH:%<tM",day_item.getTarget().getTime());
        interval.setText(day_item.getSub().getTimeInMillis()/(1000*60*60*24)+" DAYS");
        //noinspection deprecation
        interval.setBackground(day_item.getPicture());
        name.setText("  "+day_item.getName());
        date.setText("   "+sub);
        //noinspection deprecation
        tail.setBackground(day_item.getPicture());

        return item;
    }

}
