package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.ViewHolder.ListAddDogImgViewHolder;

import java.util.ArrayList;

/**
 * Created by ChiChaChai on 29-Jun-17.
 */

public class ListAddDogImgAdapter extends RecyclerView.Adapter<ListAddDogImgViewHolder> {


    Context context;
    ListAddDogImgViewHolder viewHolder;
    ArrayList<Integer> dog_img_resource = new ArrayList<>();


    public ListAddDogImgAdapter(Context context) {
        super();
        this.context = context;
    }


    @Override
    public ListAddDogImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_add_dog_img, parent, false);
        viewHolder = new ListAddDogImgViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListAddDogImgViewHolder holder, int position) {
        final int pos= position;
        //last img
        if (position == getItemCount() - 1) {
            holder.dog_img.setImageResource(R.drawable.circle_add);
            holder.dog_img.setAlpha(0.5f);
            holder.dog_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ListAddImgDog","add");
                    addImg(R.drawable.sample_0);

                }
            });
        } else {
            holder.dog_img.setImageResource(dog_img_resource.get(position));
            holder.dog_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ListAddImgDog",""+pos);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dog_img_resource.size() + 1;
    }

    public void addImg(int img_resource) {
        dog_img_resource.add(img_resource);
        for (int position = 0; position < getItemCount(); position++) {

            onBindViewHolder(viewHolder, position);

        }
    }
}
