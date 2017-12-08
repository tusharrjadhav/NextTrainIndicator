package com.test.trainindicator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.trainindicator.R;
import com.test.trainindicator.util.TrainSchedule;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private TrainSchedule ts;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.next_train));
        recyclerView = findViewById(R.id.recyclerView);
        timer = new Timer();
        ts = new TrainSchedule();
        upateStatusEverySecond();
    }

    private void updateUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Time frame for 15min
        adapter = new MyRecyclerViewAdapter(this, ts.nextTrains(Calendar.getInstance().getTime(), 15));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * To update the train schedule every second
     */
    private void upateStatusEverySecond() {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
