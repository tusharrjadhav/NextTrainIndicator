package com.test.trainindicator.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.trainindicator.R;
import com.test.trainindicator.data.Train;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tushar_temp on 18/11/17
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public static final String FORMAT_MIN_SEC = "%02d min, %02d sec";
    private List<Train> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int initialIndex;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Train> data, int index) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        initialIndex = index;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position).getDestination().getName();
        holder.myTextView.setText(title);
        int index = initialIndex + position + 1;
        holder.index.setText("" + index);
        holder.remainingTime.setText(getRemainingTime(mData.get(position)));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        public TextView remainingTime;
        public TextView index;


        public ViewHolder(View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index);
            myTextView = itemView.findViewById(R.id.title);
            remainingTime = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Train getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public String getRemainingTime(Train train) {
        Calendar calendar = Calendar.getInstance();
        long remainingTime = train.getTime().getTime() - calendar.getTime().getTime();
        return String.format(Locale.getDefault(), FORMAT_MIN_SEC,
                TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))
        );
    }

}
