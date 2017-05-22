package com.siriporn.dogfindertest;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterSameDog;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class SameDogActivity extends AppCompatActivity {
    String[] itemsPic;
    String[] pic;
    private Uri mImageCaptureUri;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        //get parameter
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Dog chosen_dog = Cache.getInstance().get("chosen_dog");
                try {
                    Response<ResponseFormat> response = DogServiceImp.getInstance().getSimilarDogFound(chosen_dog);

                    ArrayList<String> stockList = new ArrayList<>();
                    ArrayList<String> stockUri = new ArrayList<>();

                    final LostAndFound[] lostAndFounds = Converter.toPOJO(response.body().getPayload().get("lost_and_founds"), LostAndFound[].class);

                    for(LostAndFound lostAndFound: lostAndFounds) {
                        Dog dog = lostAndFound.getDog();
                        stockList.add(dog.getName()+" - FOUND");
                        stockUri.add(dog.getImages()[0]);
                    }
                    Collections.reverse(stockUri);
                    Collections.reverse(stockList);
                    Collections.reverse(Arrays.asList(lostAndFounds));


                    // INFORMATION convert List<String> to String[]
                    String[] items = new String[stockList.size()];
                    items = stockList.toArray(items);
                    // URI convert List<String> to String[]
                    itemsPic = new String[stockUri.size()];
                    itemsPic = stockUri.toArray(itemsPic);

                     GridView gridview = (GridView) findViewById(R.id.gridview);
                    CustomAdapterSameDog cus = new CustomAdapterSameDog(items, itemsPic);
                    runOnUiThread(new Runnable() {

                        GridView gridview;
                        CustomAdapterSameDog cus;

                        @Override
                        public void run() {
                            gridview.setAdapter(cus);
                        }

                        public Runnable setParams(GridView gridview, CustomAdapterSameDog cus){
                            this.gridview = gridview;
                            this.cus = cus;
                            return this;
                        }
                    }.setParams(gridview,cus));
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
                            Intent myIntent = new Intent(context, FoundPostDetail.class);
                            Cache.getInstance().put("lostAndFound", lostAndFounds[position]);
                            startActivity(myIntent);

                            String positions = Integer.toString(position);
                            Log.i("position",positions);
                                    /*Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                                    myIntent.putExtra("SelectRowDog", positions);
                                    myIntent.putExtra("Pic",itemsPic);
                                    startActivity(myIntent);*/
                        }

                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
