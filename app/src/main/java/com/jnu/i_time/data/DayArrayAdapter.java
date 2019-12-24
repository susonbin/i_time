package com.jnu.i_time.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jnu.i_time.MainActivity;
import com.jnu.i_time.R;

import java.util.ArrayList;

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
        ImageView picture=(ImageView) item.findViewById(R.id.day_item_picture);
        TextView name=(TextView)item.findViewById(R.id.textView_name);
        TextView date =(TextView) item.findViewById(R.id.textView_date);
        TextView tail=(TextView) item.findViewById(R.id.textView_tail);

        Day day_item = this.getItem(position);
        String sub=String.format("%tY年%<tm月%<td日 %<tH:%<tM",day_item.getTarget().getTime());
        interval.setText(day_item.getSub().getTimeInMillis()/(1000*60*60*24)+" DAYS");
        //noinspection deprecation
        if(day_item.getPicturePath()!=null){
            Log.d("itemID","YES"+position);
            Bitmap bmp=MainActivity.getResizePhoto(day_item.getPicturePath());
            @SuppressLint({"NewApi", "LocalSuppress"}) Bitmap blurBitmap = ImageFilter.blurBitmap(MainActivity.getActivity(), bmp, 20f);
            picture.setImageBitmap(blurBitmap);
            tail.setBackground(new BitmapDrawable(blurBitmap));
        }
        else{
            Log.d("itemID","NO"+position);
            Log.d("ID:",""+day_item.getId());
            Resources res = MainActivity.getActivity().getResources();
            Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.backgroud_1);
            @SuppressLint({"NewApi", "LocalSuppress"})Bitmap blurBitmap = ImageFilter.blurBitmap(MainActivity.getActivity(), bmp, 20f);
            picture.setImageBitmap(blurBitmap);
            tail.setBackground(new BitmapDrawable(blurBitmap));
        }

        name.setText("  "+day_item.getName());
        date.setText("   "+sub);
        //noinspection deprecation


        return item;
    }

}
