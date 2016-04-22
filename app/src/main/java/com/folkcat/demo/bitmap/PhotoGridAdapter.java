package com.folkcat.demo.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.folkcat.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamas on 2015/8/4.
 * 添加照片显示照片缩略图的适配器
 */
public class PhotoGridAdapter extends BaseAdapter {

    private List<Integer> mPhotoList;
    private Context mContext;
    private static final String TAG="PhotoGridAdapter";

    public PhotoGridAdapter(Context context) {
        this.mContext=context;
        mPhotoList=new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            mPhotoList.add(R.mipmap.test_bit_img);
        }
    }
    @Override
    public int getCount() {
        return mPhotoList.size();
    }
    @Override
    public Object getItem(int position) {
        return mPhotoList.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout newView;
        Log.i(TAG, "Position:" + position);
        newView=new LinearLayout(mContext);
        String inflater=Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li=(LayoutInflater)mContext.getSystemService(inflater);
        li.inflate(R.layout.item_photo_grid, newView,true);
        ImageView picImageView=(ImageView)newView.findViewById(R.id.iv_photo_grid_item);
        //Bitmap bitmap=BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.test_bit_img);
        Bitmap bitmap=decodeSampleBitmapFromResource(mContext.getResources(),mPhotoList.get(position),100,100);
        picImageView.setImageBitmap(bitmap);
        return newView;
    }

    private Bitmap decodeSampleBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        //First decode width inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res, resId, options);

        //Caculate inSampleSize
        options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,resId,options);

    }
    private static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        //Raw height and width of image
        int height=options.outHeight;
        int width=options.outWidth;
        Log.i(TAG,"图片原始宽度："+width+"  高度："+height);
        int inSampleSize=1;

        if(height>reqHeight||width>reqWidth){
            int halfHeight=height/2;
            int halfWidth=width/2;
            //Caculate the largest inSampleSize value that is a power of 2 and keeps both
            //height and width larger than the requested height and width.
            while((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>=reqWidth){
                inSampleSize*=2;
            }
        }
        Log.i(TAG,"InSampleSize:"+inSampleSize);
        return inSampleSize;
    }




}
