package com.test.trainindicator.data;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tushar_temp on 07/12/17
 */

public enum Destination {

    CentralStation("Cetral Station", 20, 5, 0, 23, 0),
    Circular("Circular", 60, 5, 0, 23, 0),
    NorthSquare("North Square", 12, 7, 0, 22, 0),
    WestMarket("West Market", 6, 5, 30, 25, 30);

    /**
     * Frequency in Min
     */
    private final int frequency;

    /**
     * Name of destination
     */
    private final String name;

    /**
     * Start Hr of Train for given destination
     */
    private final int startHr;

    /**
     * End Hr of Train for given destination
     */
    private final int endHr;

    /**
     * Start Min of Train for given destination
     */
    private final int startMin;

    /**
     * End Min of Train for given destination
     */
    private final int endMin;

    Destination(String name, int frequency, int startHr, int startMin, int endHr, int endMin) {
        this.name = name;
        this.frequency = frequency;
        this.startHr = startHr;
        this.startMin = startMin;
        this.endHr = endHr;
        this.endMin = endMin;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        Calendar calendar = getCalendar(startHr, startMin, 0);

        // The time of the first train
        return calendar.getTime();
    }

    public Date getEndTime() {
        Calendar calendar = getCalendar(endHr, endMin, 0);

        // The time of the first train
        return calendar.getTime();
    }

    @NonNull
    private Calendar getCalendar(int hr, int min, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
