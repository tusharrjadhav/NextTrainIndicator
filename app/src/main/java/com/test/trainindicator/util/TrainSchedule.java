package com.test.trainindicator.util;

import com.test.trainindicator.data.Destination;
import com.test.trainindicator.data.Train;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.test.trainindicator.data.Destination.CentralStation;
import static com.test.trainindicator.data.Destination.Circular;
import static com.test.trainindicator.data.Destination.NorthSquare;
import static com.test.trainindicator.data.Destination.WestMarket;

/**
 * Created by Tushar_temp on 07/12/17
 */


public class TrainSchedule {

    // Train schedule
    private TreeMap<Date, Train> scheduleMap;


    public TrainSchedule() {
        scheduleMap = new TreeMap<>();
        addTrainSchedule(CentralStation);
        addTrainSchedule(Circular);
        addTrainSchedule(NorthSquare);
        addTrainSchedule(WestMarket);
    }

    /**
     * Adding all possible train times between given time slots
     *
     * @param destination Destination enum with name, frequency, start time, end time
     */
    private void addTrainSchedule(Destination destination) {

        // Train frequency in milliseconds
        long trainFrequencyMillis = destination.getFrequency() * 60 * 1000;

        // First and last train date in milliseconds
        long firstTrainMillis = destination.getStartTime().getTime();
        long lastTrainMillis = destination.getEndTime().getTime();


        // Iterate from the first train date to the last train date to initialize the schedule
        for (long i = firstTrainMillis; i < lastTrainMillis; i += trainFrequencyMillis) {
            Date date = new Date(i);
            scheduleMap.put(date, new Train(destination, date));
        }
    }

    /**
     * Return next trains
     *
     * @param time      Current time
     * @param timeFrame Time frame in min for Next Train timing
     * @return List of next trains
     */
    public List<Train> nextTrains(Date time, int timeFrame) {
        List<Train> trainList = new ArrayList<>();

        // Current time in milliseconds
        long currentTime = time.getTime();

        // Set the time frame for schedule train time
        long timeFrameEndTime = currentTime + timeFrame * 60 * 1000;

        Date startTime = null;
        Date endTime = null;

        // You are before the first train
        if (currentTime <= scheduleMap.firstKey().getTime()) {
            startTime = scheduleMap.firstKey();

            for (Map.Entry<Date, ?> entry : scheduleMap.entrySet()) {
                if (timeFrameEndTime <= entry.getKey().getTime()) {
                    endTime = entry.getKey();
                    break;
                }
            }

        } else if (currentTime >= scheduleMap.lastKey().getTime()) {
            // You are after the last train
            // You need to take the first train from the next day schedule
            Calendar calendar = Calendar.getInstance();
            // Set the next schedule first train time using this schedule first train time + one day in milliseconds
            calendar.setTimeInMillis(scheduleMap.firstKey().getTime() + 24 * 60 * 60 * 1000);
            trainList.addAll(nextTrains(calendar.getTime(), timeFrame));
        } else {
            // We are in the schedule - go through it
            for (Map.Entry<Date, ?> entry : scheduleMap.entrySet()) {
                if (currentTime <= entry.getKey().getTime() && startTime == null) {
                    startTime = entry.getKey();
                }
                if (timeFrameEndTime <= entry.getKey().getTime()) {
                    endTime = entry.getKey();
                    break;
                }

            }
        }
        //Get the all Trains with in the given Time frame
        if (endTime != null) {
            SortedMap<Date, Train> dateTrainSortedMap = scheduleMap.subMap(startTime, endTime);
            trainList.addAll(dateTrainSortedMap.values());
        }

        return trainList;
    }

    /**
     * Get the train schedule.
     *
     * @return schedule
     */
    public TreeMap getSchedule() {
        return scheduleMap;
    }
}
