package com.test.trainindicator;

import android.util.Log;

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
    private List<Date> schedule;
    private TreeMap<Date, Train> scheduleMap;


    public TrainSchedule() {
        schedule = new ArrayList<>();

        scheduleMap = new TreeMap<>();
        addTrainSchedule(CentralStation);
        addTrainSchedule(Circular);
        addTrainSchedule(NorthSquare);
        addTrainSchedule(WestMarket);

        /* Now, iterate over the map's contents, sorted by key. */
        for (Map.Entry<Date, ?> entry : scheduleMap.entrySet()) {
            Log.i("TrainSchedule", entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Adding all possible train times between given time slots
     * @param destination Destination enum with name, frequency, start time, end time
     *
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
            schedule.add(date);
            scheduleMap.put(date, new Train(destination, date));
        }
    }

    /**
     * Return time to next train in milliseconds
     *
     * @param time
     * @return
     */
    public long timeToNextTrain(Date time) {
        // Current time in milliseconds
        long currentTime = time.getTime();

        // Check if the trains are riding
        if (currentTime <= schedule.get(0).getTime()) // You are before the first train
            return schedule.get(0).getTime() - currentTime; // Time to the first train

        if (currentTime >= schedule.get(schedule.size() - 1).getTime()) {  // You are after the last train
            // You need to take the first train from the next day schedule
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(schedule.get(0).getTime() + 24 * 60 * 60 * 1000); // Set the next schedule first train time using this schedule first train time + one day in milliseconds

            return calendar.getTimeInMillis() - currentTime;
        }

        // We are in the schedule - go through it
        for (int i = 0; i < schedule.size(); i++) {
            if (i == schedule.size() - 1) // We are at the last element, need to brake the loop
                break;
            // Time of the previous and next trains
            long previous = schedule.get(i).getTime();
            long next = schedule.get(i + 1).getTime();

            if (currentTime > previous && currentTime < next)
                return next - currentTime;

        }

        // Should never happen :)
        return -1;
    }

    /**
     * Return time to next train in milliseconds
     *
     * @param time
     * @return
     */
    public List<Train> nextTrains(Date time) {
        List<Train> trainList = new ArrayList<>();

        // Current time in milliseconds
        long currentTime = time.getTime();

        // You are before the first train
        if (currentTime <= scheduleMap.firstKey().getTime()) {
            trainList.addAll(scheduleMap.values());

        } else if (currentTime >= scheduleMap.lastKey().getTime()) {
            // You are after the last train
            // You need to take the first train from the next day schedule
            Calendar calendar = Calendar.getInstance();
            // Set the next schedule first train time using this schedule first train time + one day in milliseconds
            calendar.setTimeInMillis(schedule.get(0).getTime() + 24 * 60 * 60 * 1000);
            trainList.addAll(nextTrains(calendar.getTime()));
        } else {
            // We are in the schedule - go through it
            for (Map.Entry<Date, ?> entry : scheduleMap.entrySet()) {
                if (currentTime <= entry.getKey().getTime()) {
                    SortedMap<Date, Train> dateTrainSortedMap = scheduleMap.subMap(entry.getKey(), scheduleMap.lastKey());
                    trainList.addAll(dateTrainSortedMap.values());
                    break;
                }
            }
        }

        return trainList;
    }

    /**
     * Get the train schedule.
     *
     * @return schedule
     */
    public List<Date> getSchedule() {
        return schedule;
    }
}
