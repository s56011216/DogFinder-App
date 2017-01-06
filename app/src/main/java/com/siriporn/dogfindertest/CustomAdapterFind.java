package com.siriporn.dogfindertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterFind extends BaseAdapter {

    String items[];
    LayoutInflater mInflater;

    public CustomAdapterFind(Context context, String[] items) {
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
        ViewHolder holder = null;

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item_find,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameDogFind);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgDogFind);
            //holder.iv2 = (ImageView) convertView.findViewById(R.id.alertButton);
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
        //ImageView iv2;
        TextView tv;
    }
}