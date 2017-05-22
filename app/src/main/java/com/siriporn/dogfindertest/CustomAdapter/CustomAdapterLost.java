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
import com.siriporn.dogfindertest.R;

import static com.siriporn.dogfindertest.MainActivity.context;

public class CustomAdapterLost extends BaseAdapter {

    String itemName[];
    String itemBreed[];
    String itemNote[];
    String itemsPic[];
    String itemsDate[];
    String itemPicFB[];
    String itemNameFB[];
    String itemNotePost[];

    LayoutInflater mInflater;

    public CustomAdapterLost(Context context, String[] itemName,String[] itemBreed, String[] itemNote,String[] itemNotePost, String[] itemsPic,
                              String[] itemsDate,String[] itemPicFB , String[] itemNameFB) {

        mInflater = LayoutInflater.from(context);
        this.itemNameFB = itemNameFB;
        this.itemNote = itemNote;
        this.itemsDate = itemsDate;
        this.itemsPic = itemsPic;
        this.itemPicFB = itemPicFB;
        this.itemName = itemName;
        this.itemBreed = itemBreed;
        this.itemNotePost = itemNotePost;
    }

    @Override
    public int getCount() {return itemNameFB.length;}
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
            holder.tv2 = (TextView) convertView.findViewById(R.id.foundDatePost);
            holder.tv3 = (TextView) convertView.findViewById(R.id.noticeDog);
            holder.tv6 = (TextView) convertView.findViewById(R.id.NotePost);
            holder.ivFB = (ImageView) convertView.findViewById(R.id.lostUserPicWritePost);
            holder.ivdog = (ImageView) convertView.findViewById(R.id.picLostPost);

            holder.tv4 = (TextView) convertView.findViewById(R.id.nameLostPost);
            holder.tv5 = (TextView) convertView.findViewById(R.id.breedLostPost);


            convertView.setTag(holder);
        }

        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(itemNameFB[position]);
        holder.tv2.setText(itemsDate[position]);
        holder.tv3.setText(itemNote[position]);

        holder.tv4.setText(itemName[position]);
        holder.tv5.setText(itemBreed[position]);
        holder.tv6.setText(itemNotePost[position]);

        if (holder.ivdog != null) {
            String uri = "http://161.246.6.240:10100/server" + itemsPic[position].toString();
            Log.i("ss",uri);
            Glide.with(context)
                    .load(uri)
                    .override(500, 500)
                    .centerCrop()
                    .into(holder.ivdog);
        }

        if (holder.ivFB != null) {
            String uri = itemPicFB[position].toString();
            Glide.with(context)
                    .load(uri)
                    .override(500, 500)
                    .centerCrop()
                    .into(holder.ivFB);
        }
        return convertView;
    }

    static class ViewHolder
    {
        ImageView ivFB;
        ImageView ivdog;
        TextView tv;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
    }
}