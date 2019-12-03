package com.jnu.i_time.ui.work;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class workViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public workViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is work fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}