package com.hyman.gridview_item_rotate.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hyman.gridview_item_rotate.R;
import com.hyman.gridview_item_rotate.util.ItemCenterMeasure;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by hyman on 2015/8/8.
 */
public class GridAdapter extends BaseAdapter{
    private Context context;

    private List<String> urlArray;

    private int viewHeight;

    private int viewWidth;

    private OnItemTouchedListener listener;

    public GridAdapter(Context context, List<String> urlList) {
        super();
        this.context = context;

        this.urlArray = urlList;

        this.viewWidth = ItemCenterMeasure.ITEM_WIDTH;

        this.viewHeight = ItemCenterMeasure.ITEM_HEIGHT;


    }

    public void setUrls(List<String> urlArray) {
        this.urlArray = urlArray;
        super.notifyDataSetChanged();
    }

    public void setOnItemTouchedListener(OnItemTouchedListener onItemTouchedListener) {
        this.listener = onItemTouchedListener;
    }

    @Override
    public int getCount() {
        return urlArray == null ? 0 : urlArray.size();
    }

    @Override
    public Object getItem(int position) {
        return urlArray == null ? null : urlArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_item, parent, false);

        LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.width = viewWidth;
        layoutParams.height = viewHeight;
        convertView.setLayoutParams(layoutParams);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_image);

        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisc(true)//
                .bitmapConfig(Config.RGB_565)//
                .build();
        ImageLoader.getInstance().displayImage(urlArray.get(position),
                imageView, options);
        convertView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return listener.onItemTouch(v, event, position);
            }
        });

        return convertView;
    }

    public interface  OnItemTouchedListener {
        public boolean onItemTouch (View v, MotionEvent event, int position);
    }
}
