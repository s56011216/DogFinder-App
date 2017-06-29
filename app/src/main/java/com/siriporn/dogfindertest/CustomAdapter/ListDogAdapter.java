package com.siriporn.dogfindertest.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.ViewHolder.ListDogViewHolder;

/**
 * Created by ChiChaChai on 27-Jun-17.
 */

public class ListDogAdapter extends RecyclerView.Adapter<ListDogViewHolder> {


    private Context context;


    private int resource;
    private int[] img_res;
    private String[] name_dog;


    public ListDogAdapter(Context context, int[] img_res, String[] name_dog) {
        super();
        this.context = context;
        this.img_res = img_res;
        this.name_dog = name_dog;
    }

    @Override
    public ListDogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_dog, parent, false);
        ListDogViewHolder vh = new ListDogViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ListDogViewHolder holder, final int position) {
        if (position != 0) {
            //list pets
            holder.dog.setImageResource(img_res[position - 1]);
            holder.name_dog.setText(name_dog[position - 1]);
            holder.item_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else {
            //add pets
            holder.dog.setImageResource(R.drawable.circle_add);
            holder.dog.setAlpha(0.5f);
            holder.name_dog.setText("เพิ่มสัตว์เลี้ยง");
            holder.item_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return name_dog.length + 1;
    }

}
