package com.example.finalproject.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddLabelViewModel extends ViewModel {

    private String label;

    public AddLabelViewModel() {}

    public AddLabelViewModel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel() {
        this.label = label;
    }


}