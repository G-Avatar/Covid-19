package com.example.covid_19.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid_19.DashboardActivity;
import com.example.covid_19.SqliDatabase;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String[]> mText;

    public HomeViewModel() {
        String[] data = {"2847486","2778567","17374","51545"};
        mText = new MutableLiveData<>();
        mText.setValue(data);
    }

    public LiveData<String[]> getText() {
        return mText;
    }
}