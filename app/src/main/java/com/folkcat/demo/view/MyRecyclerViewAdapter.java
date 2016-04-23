package com.folkcat.demo.view;

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
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.SimpleItemViewHolder> {

    private static final String TAG="MyRecyclerViewAdapter";
    private List<String> items;

    public MyRecyclerViewAdapter(@NonNull List<String> dateItems) {
        this.items = (dateItems != null ? dateItems : new ArrayList<String>());
        int size=items.size();
        for(int i=0;i<size;i++){
            Log.i(TAG,items.get(i));
        }

    }

    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text_list, viewGroup, false);
        return new SimpleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleItemViewHolder viewHolder, int position) {
        viewHolder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return (this.items != null) ? this.items.size() : 0;
    }

    protected final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tv_list_item);
        }
    }
}