package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siriporn.dogfindertest.R;

import java.util.ArrayList;
import java.util.List;

import static com.siriporn.dogfindertest.MainActivity.context;

public class CustomAdapterSameDog extends BaseAdapter {
    String items[];
    String itemsPic[];
    LayoutInflater mInflater;

    public CustomAdapterSameDog(String[] items, String[] itemsPic) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.itemsPic = itemsPic;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null )
        {
            convertView = mInflater.inflate(R.layout.list_item_samedog,parent,false);
            holder = new CustomAdapterSameDog.ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameDogAccount);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgDogAccount);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String uri = "http://161.246.6.240:10100/server" + itemsPic[position].toString();
        Log.i("ss",uri);

        Glide.with(context)
                .load(uri)
                .centerCrop()
                .override(400, 400)
                .into(holder.iv);

        holder.tv.setText(items[position]);

        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv;
    }
}

