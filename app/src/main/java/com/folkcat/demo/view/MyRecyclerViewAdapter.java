package com.folkcat.demo.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.text.SimpleDateFormat;
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
        if(viewHolder.rollingFlag){
            if(!viewHolder.isRolling){
                viewHolder.isRolling=true;
                viewHolder.tv1.postDelayed(new AnimateRunnable1(viewHolder),2000);
            }
        }

    }


    @Override
    public int getItemCount() {
        return (this.items != null) ? this.items.size() : 0;
    }

    protected final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected TextView tv1;
        protected TextView tv2;
        protected boolean isRolling=false;
        protected boolean rollingFlag=true;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tv_list_item);
            this.tv1=(TextView)itemView.findViewById(R.id.tv_text);
            this.tv2=(TextView)itemView.findViewById(R.id.tv_text2);
        }
    }

    private class AnimateRunnable1 implements Runnable{
        private SimpleItemViewHolder holder;
        public AnimateRunnable1(SimpleItemViewHolder holder){
            this.holder=holder;
        }
        public void run(){
            if(!holder.rollingFlag){
                return;
            }
            Animation animation= AnimationUtils.loadAnimation(holder.tv1.getContext(),R.anim.text_roll);
            Animation animation2= AnimationUtils.loadAnimation(holder.tv1.getContext(),R.anim.text_roll2);
            holder.tv1.startAnimation(animation);
            holder.tv2.startAnimation(animation2);
            holder.tv1.postDelayed(new AnimateRunnable2(holder),2000);
        }
    }
    private class AnimateRunnable2 implements Runnable{
        private SimpleItemViewHolder holder;
        public AnimateRunnable2(SimpleItemViewHolder holder){
            this.holder=holder;
        }
        public void run(){
            if(!holder.rollingFlag){
                return;
            }
            Animation animation= AnimationUtils.loadAnimation(holder.tv1.getContext(),R.anim.text_roll);
            Animation animation2= AnimationUtils.loadAnimation(holder.tv1.getContext(),R.anim.text_roll2);
            holder.tv1.startAnimation(animation2);
            holder.tv2.startAnimation(animation);
            holder.tv1.postDelayed(new AnimateRunnable1(holder),2000);
        }
    }
}