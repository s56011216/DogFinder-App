package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.siriporn.dogfindertest.Cache;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterFound;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterLost;
import com.siriporn.dogfindertest.FoundPostDetail;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by siriporn on 26/12/2559.
 */

public class LostFragment extends Fragment {

    String[] itemName, itemBreed, itemNote,itemPic, itemsPicFB, itemsDate,itemsNameFB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.layout_lost, container, false);
        /**
         * my dog
         */

        DogServiceImp.getInstance().getAllLostAndFound(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");

                    ArrayList<String> nameList = new ArrayList<String>();
                    ArrayList<String> breedList = new ArrayList<String>();
                    ArrayList<String> noteList = new ArrayList<String>();
                    ArrayList<String> dateList = new ArrayList<String>();
                    ArrayList<String> FBnameList = new ArrayList<String>();
                    ArrayList<String> FBpicList = new ArrayList<String>();
                    ArrayList<String> stockUri = new ArrayList<String>();

                    final LostAndFound[] lostAndFounds = Converter.toPOJO(response.body().getPayload().get("lost_and_founds"), LostAndFound[].class);

                    for(LostAndFound lostAndFound: lostAndFounds) {
                        if(lostAndFound.getType() == 0){
                        Dog dog = lostAndFound.getDog();
                        //get name
                        nameList.add(dog.getName());

                        //get breed
                        breedList.add(dog.getBleed());

                        //get note
                        noteList.add(dog.getNote());
                        //get dog image
                        String[] images = dog.getImages();

                        if (images.length != 0) {
                            stockUri.add(images[0]);
                        } else { //temporary
                            stockUri.add(images[0]);
                        }

                        //get date
                        dateList.add(dog.getCreated_at().toString());

                        //create user
                        User user = dog.getUser();
                        FBnameList.add(user.getFb_name());
                        FBpicList.add(user.getFb_profile_image());
                    }
                    }
                    // NAME convert List<String> to String[]
                    itemName = new String[nameList.size()];
                    itemName = nameList.toArray(itemName);
                    // NAME convert List<String> to String[]
                    itemBreed = new String[breedList.size()];
                    itemBreed = breedList.toArray(itemBreed);

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


                    ListView list = (ListView) myView.findViewById(R.id.lostListView);
                    CustomAdapterLost cus = new CustomAdapterLost(getActivity(), itemName, itemBreed, itemNote,
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
                            Intent myIntent = new Intent(getActivity(), FoundPostDetail.class);
                            Cache.getInstance().put("lostAndFound", lostAndFounds[position]);
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
