package com.jnu.i_time.ui.anniversary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnniversaryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AnniversaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is anniversary fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}