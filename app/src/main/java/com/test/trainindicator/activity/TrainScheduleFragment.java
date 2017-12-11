package com.test.trainindicator.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.trainindicator.R;
import com.test.trainindicator.data.Train;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tushar_temp on 08/12/17
 */

public class TrainScheduleFragment extends Fragment {

    public static final String EXTRA_TRAINS = "EXTRA_TRAINS";
    public static final String EXTRA_TRAINS_INDEX = "EXTRA_TRAINS_INDEX";
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;


    //region Constructor method
    public static TrainScheduleFragment instance(ArrayList<Train> trainList, int index) {
        TrainScheduleFragment fragment = new TrainScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TRAINS, trainList);
        bundle.putInt(EXTRA_TRAINS_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    //endregion

    //region Fragment Lifecycle
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_train, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        updateUI();
        return rootView;
    }
    //endregion


    //region custom methods
    private void updateUI() {
        List<Train> trainList = (List<Train>) getArguments().getSerializable(EXTRA_TRAINS);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), trainList, getArguments().getInt(EXTRA_TRAINS_INDEX, 0));
        recyclerView.setAdapter(adapter);
    }
    //endregion

}
