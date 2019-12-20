package com.jnu.i_time.ui.live;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jnu.i_time.MainActivity;
import com.jnu.i_time.R;
import com.jnu.i_time.data.Day;
import com.jnu.i_time.data.DayArrayAdapter;

import java.util.ArrayList;

public class LiveViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<DayArrayAdapter> mAdapter;
    private Context context;

    private ArrayList<Day> days_of_live;

    public LiveViewModel() {

        context= MainActivity.getContext();
        days_of_live =MainActivity.getDays();

        mText = new MutableLiveData<>();
        mText.setValue("This is live fragment");

        mAdapter=new MutableLiveData<>();
        mAdapter.setValue(new DayArrayAdapter(context, R.layout.day_item/*具体的试图*/, days_of_live));
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<DayArrayAdapter> getAdapter(){
        return mAdapter;
    }
    public ArrayList<Day> getDays_of_live() {
        return days_of_live;
    }
}