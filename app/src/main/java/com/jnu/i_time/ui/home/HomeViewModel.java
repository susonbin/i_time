package com.jnu.i_time.ui.home;

import android.app.Application;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jnu.i_time.Day;
import com.jnu.i_time.MainActivity;
import com.jnu.i_time.R;

import java.util.ArrayList;
import java.util.List;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<DayArrayAdapter> mAdapter;
    private Context context;


    public HomeViewModel() {

        context=MainActivity.getContext();
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mAdapter=new MutableLiveData<>();

        mAdapter.setValue(new DayArrayAdapter(context,R.layout.day_item/*具体的试图*/, MainActivity.getDays()));
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<DayArrayAdapter> getAdapter(){
        return mAdapter;
    }

}
