package com.test.trainindicator.data;

import java.util.Date;

/**
 * Created by Tushar_temp on 08/12/17
 */

public class Train {

    private Destination destination;
    private Date time;

    public Train(Destination destination, Date time) {
        this.destination = destination;
        this.time = time;
    }

    public Destination getDestination() {
        return destination;
    }

    public Date getTime() {
        return time;
    }
}
