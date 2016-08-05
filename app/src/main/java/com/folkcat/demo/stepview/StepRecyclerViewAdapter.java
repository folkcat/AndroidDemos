package com.folkcat.demo.stepview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamas on 2016/4/23.
 */
public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.SimpleItemViewHolder> {

    private static final String TAG="StepRecyclerViewAdapter";
    private List<Step> itemList;

    public StepRecyclerViewAdapter(@NonNull List<Step> dateItems) {
        this.itemList = (dateItems != null ? dateItems : new ArrayList<Step>());
        int size=itemList.size();
    }

    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step_view, viewGroup, false);
        return new SimpleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleItemViewHolder viewHolder, int position) {
        viewHolder.leftTextView.setText(itemList.get(position).getLeftString());
        viewHolder.rightTextView.setText(itemList.get(position).getRightString());
    }

    @Override
    public int getItemCount() {
        return (this.itemList != null) ? this.itemList.size() : 0;
    }

    protected final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView leftTextView;
        protected TextView rightTextView;


        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            this.leftTextView = (TextView) itemView.findViewById(R.id.tv_step_left);
            this.rightTextView=(TextView) itemView.findViewById(R.id.tv_step_right);

        }
    }
}