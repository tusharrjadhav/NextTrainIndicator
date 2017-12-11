package com.test.trainindicator.activity;

import com.test.trainindicator.data.Train;
import com.test.trainindicator.util.TrainSchedule;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tushar_temp on 11/12/17
 */

public class TrainSchedulePresenter {


    private TrainSchedule trainSchedule;

    private TrainScheduleView view;

    private Timer timer;


    public TrainSchedulePresenter(TrainScheduleView view) {
        this.trainSchedule = new TrainSchedule();
        this.view = view;
    }

    /**
     * To update the train schedule every second
     *
     * @param timeFrame Train schedule time frame
     */
    public void startTrainScheduleUpdate(int timeFrame) {
        final List<Train> trains = trainSchedule.nextTrains(Calendar.getInstance().getTime(), timeFrame);
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                view.updateUI(trains);
            }
        };
        timer.schedule(timerTask, 0, 10000);
    }

    /**
     * To update the train schedule every second
     *
     * @param timeFrame Refresh time frame
     */
    public void refreshTrainScheduleTimeFrame(int timeFrame) {
        stopTrainScheduleUpdate();
        startTrainScheduleUpdate(timeFrame);
    }

    public void stopTrainScheduleUpdate() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
