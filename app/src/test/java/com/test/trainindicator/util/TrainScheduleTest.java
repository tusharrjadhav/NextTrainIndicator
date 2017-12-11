package com.test.trainindicator.util;

import com.test.trainindicator.data.Train;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Tushar_temp on 10/12/17
 */
public class TrainScheduleTest {

    private TrainSchedule trainSchedule;

    @Before
    public void setUp() throws Exception {
        trainSchedule = new TrainSchedule();
    }

    @Test
    public void testSchedule() throws Exception {
        assertNotNull(trainSchedule.getSchedule());
        assertFalse(trainSchedule.getSchedule().isEmpty());
    }

    @Test
    public void testNextTrainInBetweenDay() throws Exception {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 7);
        List<Train> trains = trainSchedule.nextTrains(instance.getTime(), 60);
        assertNotNull(trains);
        assertFalse(trains.isEmpty());
    }

    @Test
    public void testNextTrainStartOfDay() throws Exception {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 4);
        instance.set(Calendar.MINUTE, 30);
        List<Train> trains = trainSchedule.nextTrains(instance.getTime(), 60);
        assertNotNull(trains);
        assertFalse(trains.isEmpty());
    }
}