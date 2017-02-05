package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterSameDog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SameDogActivity extends AppCompatActivity {
    String[] itemsPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");
                    ArrayList<String> stockList = new ArrayList<String>();
                    ArrayList<String> stockUri = new ArrayList<String>();

                    final List<Map<String, Object>> dogs = (List<Map<String, Object>>) response.body().getPayload().get("dogs");

                    for(int i = 0 ; i< dogs.size()-1 ; i++) {
                        // INFORMATION AND URI convert List<String> to String[]
                        stockList.add(dogs.get(i).get("name").toString());
                        List<String> imagesUrl = (List<String>) dogs.get(i).get("images");
                        if(imagesUrl.size() != 0) {
                            stockUri.add(imagesUrl.get(0));
                        }else{ //temporary
                            imagesUrl = (List<String>) dogs.get(0).get("images");
                            stockUri.add(imagesUrl.get(0));
                            Log.i("picture : ",Integer.toString(i));

                        }

                    }
                    // INFORMATION convert List<String> to String[]
                    String[] items = new String[stockList.size()];
                    items = stockList.toArray(items);
                    // URI convert List<String> to String[]
                    itemsPic = new String[stockUri.size()];
                    itemsPic = stockUri.toArray(itemsPic);

                    GridView gridview = (GridView) findViewById(R.id.gridview);
                    CustomAdapterSameDog cus = new CustomAdapterSameDog( items, itemsPic);
                    gridview.setAdapter(cus);


                    //When Clicked
                        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                        long arg3) {
                                    // TODO Auto-generated method stub
                                    //Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                                    /**
                                     * Send position for showing in Dog detail on next page (MyDogDetail)
                                     */
                                    String positions = Integer.toString(position);
                                    /*Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                                    myIntent.putExtra("SelectRowDog", positions);
                                    myIntent.putExtra("Pic",itemsPic);
                                    startActivity(myIntent);*/
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
    }

}
