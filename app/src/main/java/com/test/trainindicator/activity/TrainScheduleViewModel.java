package com.test.trainindicator.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.test.trainindicator.data.Train;
import com.test.trainindicator.util.TrainSchedule;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tushar_temp on 11/12/17
 */

public class TrainScheduleViewModel extends ViewModel {


    private TrainSchedule trainSchedule;

    private int timeFrame;

    Timer timer;

    private MutableLiveData<List<Train>> trainsLiveData = new MutableLiveData<>();


    public TrainScheduleViewModel(int timeFrame) {
        this.timeFrame = timeFrame;
        startTrainScheduleUpdate();
    }

    /**
     * To update the train schedule every second
     *
     */
    private void startTrainScheduleUpdate() {
        this.trainSchedule = new TrainSchedule();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TrainScheduleViewModel.class.getSimpleName(), "timerTask with TimeFrame: " + timeFrame);
                List<Train> trains = trainSchedule.nextTrains(Calendar.getInstance().getTime(), timeFrame);
                trainsLiveData.postValue(trains);
            }
        };
        timer.schedule(timerTask, 0, 10000);
    }

    public MutableLiveData<List<Train>> getTrainsLiveData() {
        return trainsLiveData;
    }

    /**
     * To update the train schedule every second
     *
     * @param timeFrame Refresh time frame
     */
    public void refreshTrainScheduleTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
    }

    public void stopTrainScheduleUpdate() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
