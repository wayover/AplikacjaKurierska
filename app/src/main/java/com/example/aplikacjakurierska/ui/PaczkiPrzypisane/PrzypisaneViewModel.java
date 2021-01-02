package com.example.aplikacjakurierska.ui.PaczkiPrzypisane;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrzypisaneViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrzypisaneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Twoje paczki");
    }

    public LiveData<String> getText() {
        return mText;
    }
}