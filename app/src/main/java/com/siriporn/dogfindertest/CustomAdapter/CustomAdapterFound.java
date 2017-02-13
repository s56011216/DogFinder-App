package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.siriporn.dogfindertest.R;

import static com.siriporn.dogfindertest.MainActivity.context;

public class CustomAdapterFound extends BaseAdapter {

    String items[];
    String itemsDate[];
    String itemsPic[];
    String itemsPicFB[];
    LayoutInflater mInflater;

    public CustomAdapterFound(Context context, String[] items, String[] itemsDate, String[] itemsPic, String[] itemsPicFB) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.itemsDate = itemsDate;
        this.itemsPic = itemsPic;
        this.itemsPicFB = itemsPicFB;
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
            holder.iv2 = (ImageView) convertView.findViewById(R.id.picFoundPost);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(items[position]);
        holder.tv2.setText(itemsDate[position]);
        //Dog Pic
        if (holder.iv2 != null) {
            String uri = "http://161.246.6.240:10100/server" + itemsPic[position].toString();
            Log.i("ss",uri);
            Glide.with(context)
                    .load(uri)
                    .override(500, 500)
                    .centerCrop()
                    .into(holder.iv);
        }
        //FB Pic
        Profile profile = Profile.getCurrentProfile();
        if (holder.iv != null) {
            String uri = itemsPicFB[position].toString();
            Glide.with(context)
                    .load(uri)
                    .override(500, 500)
                    .centerCrop()
                    .into(holder.iv);
        }
        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        ImageView iv2;
        TextView tv;
        TextView tv2;
    }
}