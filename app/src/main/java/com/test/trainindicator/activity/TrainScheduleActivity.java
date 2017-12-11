package com.test.trainindicator.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.darwindeveloper.wcviewpager.WCViewPagerIndicator;
import com.test.trainindicator.R;
import com.test.trainindicator.data.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainScheduleActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TrainScheduleView {

    private WCViewPagerIndicator wcViewPagerIndicator;
    private TextView emptyMsgTx;

    private EditText timeFrameEt;

    private Button refreshButton;

    private TrainSchedulePresenter presenter;

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

        timeFrameEt = findViewById(R.id.editTimeFrame);
        timeFrameEt.setText("" + timeFrame);

        refreshButton = findViewById(R.id.refreshBt);

        refreshButton.setOnClickListener(view -> {
            timeFrame = Integer.valueOf(timeFrameEt.getText().toString());
            presenter.refreshTrainScheduleTimeFrame(timeFrame);
        });

        wcViewPagerIndicator = findViewById(R.id.wcviewpager);
        wcViewPagerIndicator.getViewPager().addOnPageChangeListener(this);

        presenter = new TrainSchedulePresenter(this);

        presenter.startTrainScheduleUpdate(timeFrame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopTrainScheduleUpdate();
    }
    //endregion


    //region OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        wcViewPagerIndicator.setSelectedindicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //endregion

    //region Custom Methods
    public void updateUI(List<Train> trains) {
        runOnUiThread(() -> {
            timeFrameEt.setText("" + timeFrame);

            if (trains.isEmpty()) {
                emptyMsgTx.setVisibility(View.VISIBLE);
                wcViewPagerIndicator.setVisibility(View.GONE);
            } else {
                emptyMsgTx.setVisibility(View.GONE);
                wcViewPagerIndicator.setVisibility(View.VISIBLE);
                MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), trains);
                wcViewPagerIndicator.setAdapter(mAdapter);
            }
        });

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
