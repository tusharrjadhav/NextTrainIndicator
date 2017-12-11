package com.test.trainindicator.activity;

import com.test.trainindicator.data.Train;

import java.util.List;

/**
 * Created by Tushar_temp on 11/12/17
 */

public interface TrainScheduleView {
    void updateUI(List<Train> trains);
}
