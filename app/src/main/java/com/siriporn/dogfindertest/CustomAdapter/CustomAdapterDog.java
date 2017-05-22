package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siriporn.dogfindertest.R;

import static com.siriporn.dogfindertest.MainActivity.context;

public class CustomAdapterDog extends BaseAdapter {

    String items[], items2[];
    String itemsPic[];
    LayoutInflater mInflater;

    public CustomAdapterDog(Context context, String[] items, String[] itemsPic, String[] items2) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.items2 = items2;
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

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item_mydog,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameDogAccount);
            holder.tv2 = (TextView) convertView.findViewById(R.id.breedText);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgDogAccount);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(items[position]);
        holder.tv2.setText(items2[position]);

        if (holder.iv != null) {
            String uri = "http://161.246.6.240:10100/server" + itemsPic[position].toString();
            Log.i("ss",uri);
            Glide.with(context)
                    .load(uri)
                    .override(400, 400)
                    .centerCrop()
                    .into(holder.iv);
        }

        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv;
        TextView tv2;
    }

}