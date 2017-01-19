package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siriporn.dogfindertest.R;

public class CustomAdapterLost extends BaseAdapter {

    String items[];
    LayoutInflater mInflater;

    public CustomAdapterLost(Context context, String[] items) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
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

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item_lost,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameFB);
            holder.iv = (ImageView) convertView.findViewById(R.id.lostUserPicPost);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(items[position]);
        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv;
    }
}