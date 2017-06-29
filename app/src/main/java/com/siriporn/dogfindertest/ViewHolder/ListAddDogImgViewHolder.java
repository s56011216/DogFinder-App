package com.siriporn.dogfindertest.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.Utilty.CircleImageView;

/**
 * Created by ChiChaChai on 29-Jun-17.
 */

public class ListAddDogImgViewHolder extends RecyclerView.ViewHolder {
    View view;
    public CircleImageView dog_img;

    public ListAddDogImgViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        dog_img = (CircleImageView) view.findViewById(R.id.add_dog_img);


    }
}
