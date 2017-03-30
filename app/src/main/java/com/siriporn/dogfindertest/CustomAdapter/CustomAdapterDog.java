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
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.R;

import java.util.List;

import static android.R.id.list;
import static com.siriporn.dogfindertest.MainActivity.context;

public class CustomAdapterDog extends BaseAdapter {

    private Context mContext;
    private List<Dog> mProductList;

    public CustomAdapterDog(Context mContext, List<Dog> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    public void addListItemToAdapter(List<Dog> list) {
        //Add list to current array list of data
        mProductList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProductList.size();
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

        View v = View.inflate(mContext, R.layout.list_item_mydog, null);

        TextView name = (TextView) v.findViewById(R.id.nameDogAccount);
        ImageView img = (ImageView) v.findViewById(R.id.imgDogAccount);

        //Set text for TextView
        name.setText(mProductList.get(position).getName().toString());
        String[] pic = mProductList.get(position).getImages();
            String uri = "http://161.246.6.240:10100/server" + pic[0].toString();
            Glide.with(context)
                    .load(uri)
                    .override(400, 400)
                    .centerCrop()
                    .into(img);

        return v;
    }

}