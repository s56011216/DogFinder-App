package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.layout_mydog, container, false);

        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");
                    ArrayList<String> stockList = new ArrayList<String>();
                    final List<Map<String, Object>> dogs = (List<Map<String, Object>>) response.body().getPayload().get("dogs");

                    for(int i = 0 ; i< dogs.size() ; i++) {
                        dogs.get(i).get("name");
                        stockList.add(dogs.get(i).get("name").toString());
                        //Log.i("Name", dogs.get(i).get("name"));
                    }

                    String[] items = new String[stockList.size()];
                    items = stockList.toArray(items);

                    for(String s : items)
                        Log.i("items",s);

                    //final String[] items = new String[] { "Nancy", "ThongDee", "Boo","Carrot" };

                    ListView list = (ListView)myView.findViewById(R.id.dogListView);
                    CustomAdapterDog cus = new CustomAdapterDog(getActivity(),items);
                    list.setAdapter(cus);


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long arg3) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                            String positions = Integer.toString(position);

                            // to MyDogDetail
                            Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                            myIntent.putExtra("SelectRowDog", positions);
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
