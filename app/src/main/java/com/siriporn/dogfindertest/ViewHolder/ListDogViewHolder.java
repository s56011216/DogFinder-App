package com.siriporn.dogfindertest.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.Utilty.CircleImageView;

/**
 * Created by ChiChaChai on 28-Jun-17.
 */

public class ListDogViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView dog;
    public TextView name_dog;
    public RelativeLayout item_box;

    public ListDogViewHolder(View itemView) {
        super(itemView);
        dog = (CircleImageView) itemView.findViewById(R.id.list_dog_img);
        name_dog = (TextView) itemView.findViewById(R.id.list_dog_text);
        item_box = (RelativeLayout)itemView.findViewById(R.id.list_dog_item);

    }


}
