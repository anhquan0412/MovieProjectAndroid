package com.anhquan.movieproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by anhqu on 7/4/2016.
 */
public class ImageAdapter extends BaseAdapter {
    Context context;
    String[] picUrls = null;

    public ImageAdapter(Context c,String[] s)
    {
        context = c;
        picUrls = s;
    }

    @Override
    public int getCount() {
        if(picUrls==null)
            return 0;
        return picUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return picUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        //initialize attributes for new view (not recycled yet)
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3,3,3,3);

        }
        else {
            imageView = (ImageView) convertView;
        }
        String url = (String) getItem(position);

        Picasso.with(context).load(url).into(imageView);

        return imageView;
    }
}
