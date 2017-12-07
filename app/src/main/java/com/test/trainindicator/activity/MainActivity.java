package com.test.trainindicator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.trainindicator.R;
import com.test.trainindicator.TrainSchedule;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private TrainSchedule ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        ts = new TrainSchedule();
        updateUI();
//        getTrainSchedule();
    }

    private void updateUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, ts.nextTrains(Calendar.getInstance().getTime()));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void getTrainSchedule() {
        Calendar calendar = Calendar.getInstance();

        TrainSchedule ts = new TrainSchedule();


        System.out.println("=========================================");

        // Let's check how much time we have to the next train
        System.out.println("It's " + calendar.getTime() + " you have " + ts.timeToNextTrain(calendar.getTime()) / 1000 + " seconds to the next train");

        // Let's check how it would behave when we are before the schedule
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        System.out.println("It's " + calendar.getTime() + " you have " + ts.timeToNextTrain(calendar.getTime()) / 1000 + " seconds to the first train from today's schedule");

        // Let's check how it would behave when we are before the schedule
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        System.out.println("It's " + calendar.getTime() + " you have " + ts.timeToNextTrain(calendar.getTime()) / 1000 + " seconds to the first train from the next schedule");
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
