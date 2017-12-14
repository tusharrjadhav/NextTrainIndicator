package com.test.trainindicator.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Tushar_temp on 14/12/17
 */

public class TrainScheduleViewModelFactory implements ViewModelProvider.Factory {

    private int timeFrame;

    public TrainScheduleViewModelFactory(int timeFrame) {
        this.timeFrame = timeFrame;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TrainScheduleViewModel.class)) {
            return (T) new TrainScheduleViewModel(timeFrame);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
