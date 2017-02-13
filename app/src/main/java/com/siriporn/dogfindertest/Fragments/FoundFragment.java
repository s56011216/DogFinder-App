package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import com.facebook.Profile;
import com.siriporn.dogfindertest.Cache;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterFound;
import com.siriporn.dogfindertest.FoundDetail;
import com.siriporn.dogfindertest.MainActivity;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.MyDogDetail;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by siriporn on 26/12/2559.
 */

public class FoundFragment extends Fragment {
    String[] itemId, itemNote,itemPic, itemsPicFB, itemsDate,itemsNameFB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.layout_found, container, false);
        /**
         * my dog
         */

        DogServiceImp.getInstance().getAllLostAndFound(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");

                    ArrayList<String> stockList = new ArrayList<String>();
                    ArrayList<String> noteList = new ArrayList<String>();
                    ArrayList<String> dateList = new ArrayList<String>();
                    ArrayList<String> FBnameList = new ArrayList<String>();
                    ArrayList<String> FBpicList = new ArrayList<String>();
                    ArrayList<String> stockUri = new ArrayList<String>();

                    final List<Map<String, Object>> lost_and_founds = (List<Map<String, Object>>) response.body().getPayload().get("lost_and_founds");

                        for(int i = 0 ; i < lost_and_founds.size() ; i++) {
                            //if(lostAndFound.getDog().getId() == dogs.get(i).get("id")){
                            // INFORMATION AND URI convert List<String> to String[]
                            Map<String, Object> dogs = (Map) lost_and_founds.get(i).get("dog");
                            //if (dogs.get("type").toString() == "1") {

                                //get note
                                noteList.add(dogs.get("note").toString());
                                //get dog image
                                List<String> imagesUrl = (List<String>) dogs.get("images");

                                if (imagesUrl.size() != 0) {
                                    stockUri.add(imagesUrl.get(0));
                                } else { //temporary
                                    imagesUrl = (List<String>) dogs.get("images");
                                    stockUri.add(imagesUrl.get(0));
                                    Log.i("picture : ", Integer.toString(i));
                                }

                                //get date
                                dateList.add(dogs.get("created_at").toString());

                                //create user
                                Map<String, Object> user = (Map<String, Object>) dogs.get("user");
                                FBnameList.add(user.get("fb_name").toString());
                                FBpicList.add(user.get("fb_profile_image").toString());

                            }

                            // NOTE convert List<String> to String[]
                            itemNote = new String[noteList.size()];
                            itemNote = noteList.toArray(itemNote);
                            // DOG IMG convert List<String> to String[]
                            itemPic = new String[stockUri.size()];
                            itemPic = stockUri.toArray(itemPic);
                            // DATE convert List<String> to String[]
                            itemsDate = new String[dateList.size()];
                            itemsDate = dateList.toArray(itemsDate);

                            // FB IMG convert List<String> to String[]
                            itemsPicFB = new String[FBpicList.size()];
                            itemsPicFB = FBpicList.toArray(itemsPicFB);
                            // FB NAME convert List<String> to String[]
                            itemsNameFB = new String[FBnameList.size()];
                            itemsNameFB = FBnameList.toArray(itemsNameFB);


                            ListView list = (ListView) myView.findViewById(R.id.foundListView);
                            CustomAdapterFound cus = new CustomAdapterFound(getActivity(), itemId, itemNote,
                                    itemPic, itemsDate, itemsPicFB, itemsNameFB);
                            list.setAdapter(cus);


                            //When Clicked
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                        long arg3) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getActivity(), "row : " + position, Toast.LENGTH_SHORT).show();

                                    /**
                                     * Send position for showing in Dog detail on next page (ProfileFragment)
                                     */
                                    String positions = Integer.toString(position);
                                    Intent myIntent = new Intent(getActivity(), FoundDetail.class);
                                    Cache.getInstance().put("lostAndFound", lost_and_founds);
                                    startActivity(myIntent);
                                }

                            });
                      //  }
                }
                else{
                    Log.e("Sucess","Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("Sucess","onFailure");
            }
        });

        return myView;

    }
}
