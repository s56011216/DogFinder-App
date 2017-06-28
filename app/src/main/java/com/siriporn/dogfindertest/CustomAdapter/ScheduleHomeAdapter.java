package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.Utilty.CircleImageView;

/**
 * Created by ChiChaChai on 28-Jun-17.
 */

public class ScheduleHomeAdapter extends BaseAdapter {


    Context context;
    int[] dog_img_resource;
    String[] title_text;
    String[] message_text;

    CircleImageView dog_img;
    TextView title;
    TextView message;

    public ScheduleHomeAdapter(Context context, int[] dog_img_resource, String[] title_text, String[] message_text) {
        this.context = context;
        this.dog_img_resource = dog_img_resource;
        this.title_text = title_text;
        this.message_text = message_text;
    }

    @Override
    public int getCount() {
        return title_text.length;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.list_schedule_home, parent, false);
        dog_img = (CircleImageView)convertView.findViewById(R.id.schedule_home_img);
        title = (TextView)convertView.findViewById(R.id.schedule_home_title);
        message = (TextView)convertView.findViewById(R.id.schedule_home_message);

        dog_img.setImageResource(dog_img_resource[position]);


        return convertView;
    }
}
