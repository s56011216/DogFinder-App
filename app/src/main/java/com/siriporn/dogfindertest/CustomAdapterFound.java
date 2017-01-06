package com.siriporn.dogfindertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterFound extends BaseAdapter {

    String items[];
    String itemsDate[];
    LayoutInflater mInflater;

    public CustomAdapterFound(Context context, String[] items, String[] itemsDate) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.itemsDate = itemsDate;
    }

    @Override
    public int getCount() {return items.length;}
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
            convertView = mInflater.inflate(R.layout.list_item_found,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameFB);
            holder.tv2 = (TextView) convertView.findViewById(R.id.foundDatePost);
            holder.iv = (ImageView) convertView.findViewById(R.id.foundUserPicPost);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(items[position]);
        holder.tv2.setText(itemsDate[position]);
        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv;
        TextView tv2;
    }
}