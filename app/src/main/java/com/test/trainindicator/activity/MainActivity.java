package com.test.trainindicator.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.test.trainindicator.R;
import com.test.trainindicator.data.Train;
import com.test.trainindicator.util.TrainSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView emptyMsgTx;
    private TrainSchedule ts;
    private Timer timer;
    //Time frame for 15min
    private int timeFrame = 15;

    //region Activity Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.next_train));
        emptyMsgTx = findViewById(R.id.empty_msg);
        emptyMsgTx.setText(getString(R.string.empty_list_msg, "" + timeFrame));
        viewPager = findViewById(R.id.pager);
        timer = new Timer();
        ts = new TrainSchedule();


        upateStatusEverySecond();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
    //endregion

    //region Custom Methods
    private void updateUI() {

        List<Train> trains = ts.nextTrains(Calendar.getInstance().getTime(), timeFrame);


        if (trains.isEmpty()) {
            emptyMsgTx.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        } else {
            emptyMsgTx.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);

            MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), trains);
            viewPager.setAdapter(mAdapter);
        }
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
        }, 0, 10000);
    }
    //endregion

    /**
     * Inner static class
     */
    public static class MyAdapter extends FragmentPagerAdapter {

        List<Train> trainList;

        MyAdapter(FragmentManager fm, List<Train> trains) {
            super(fm);
            trainList = trains;
        }

        @Override
        public int getCount() {
            return (int) Math.floor(trainList.size() / 3);
        }

        @Override
        public Fragment getItem(int position) {

            ArrayList<Train> subTrains = new ArrayList<>(trainList.subList(position * 3, position * 3 + 3));

            return TrainScheduleFragment.instance(subTrains, position * 3);
        }

    }

}
