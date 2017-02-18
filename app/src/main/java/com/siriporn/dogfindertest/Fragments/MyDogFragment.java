package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.MyDogDetail;
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

public class MyDogFragment extends Fragment {
    String[] itemsPic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.layout_mydog, container, false);
        /**
         * my dog
         */
        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");
                    ArrayList<String> stockList = new ArrayList<>();
                    ArrayList<String> stockUri = new ArrayList<>();
                    ArrayList<Double> latList = new ArrayList<>();
                    ArrayList<Double> longList = new ArrayList<>();
                    final Dog[] dogs = Converter.toPOJO(response.body().getPayload().get("dogs"), Dog[].class);

                    for(Dog dog: dogs) {
                        stockList.add(dog.getName());
                        latList.add(dog.getLatitude());
                        longList.add(dog.getLongitude());
                        //if(dog.getImages().length != 0) {
                        stockUri.add(dog.getImages()[0]);
                        //}else{ //temporary
                        //    stockUri.add(dogs[0].getImages()[0]);
                        //}
                    }

                    // INFORMATION convert List<String> to String[]
                    String[] items = new String[stockList.size()];
                    items = stockList.toArray(items);
                    // URI convert List<String> to String[]
                    itemsPic = new String[stockUri.size()];
                    itemsPic = stockUri.toArray(itemsPic);
                    //convert double list to double[]
                    final double[] lat_list = new double[latList.size()];
                    for (int i = 0; i < lat_list.length; i++) {
                        lat_list[i] = latList.get(i).doubleValue();
                    }
                    final double[] long_list = new double[longList.size()];
                    for (int i = 0; i < long_list.length; i++) {
                        long_list[i] = longList.get(i).doubleValue();
                    }



                    ListView list = (ListView)myView.findViewById(R.id.dogListView);
                    CustomAdapterDog cus = new CustomAdapterDog(getActivity(),items,itemsPic);
                    list.setAdapter(cus);


                    //When Clicked
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long arg3) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                            /**
                             * Send position for showing in Dog detail on next page (MyDogDetail)
                             */
                            String positions = Integer.toString(position);
                            Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                            myIntent.putExtra("SelectRowDog", positions);
                            myIntent.putExtra("Pic",itemsPic);
                            myIntent.putExtra("lat",lat_list);
                            myIntent.putExtra("lon",long_list);


                            startActivity(myIntent);
                        }

                    });

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
