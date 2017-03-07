package com.siriporn.dogfindertest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterFound;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterSearch;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class SameDogSearchActivity extends AppCompatActivity {
    String[] itemName, itemBreed, itemNote, itemPic, itemsPicFB, itemsDate, itemsNameFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_dog_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File file = Cache.getInstance().get("PictureILike");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), requestFile);
        DogServiceImp.getInstance().getSimilarDogFound("oil", body, new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if (response.body().isSuccess()) {
                    ArrayList<String> noteList = new ArrayList<String>();
                    ArrayList<String> dateList = new ArrayList<String>();
                    ArrayList<String> FBnameList = new ArrayList<String>();
                    ArrayList<String> FBpicList = new ArrayList<String>();
                    ArrayList<String> stockUri = new ArrayList<String>();

                    LostAndFound[] lostAndFounds = Converter.toPOJO(response.body().getPayload().get("lost_and_founds"), LostAndFound[].class);

                    for (LostAndFound lostAndFound : lostAndFounds) {
                        if (lostAndFound.getType() == 1) {
                            Dog dog = lostAndFound.getDog();
                            //get note
                            noteList.add(lostAndFound.getNote());
                            //get dog image
                            String[] images = dog.getImages();

                            if (images.length != 0) {
                                stockUri.add(images[0]);
                            } else { //temporary
                                stockUri.add(images[0]);
                            }

                            //get date
                            dateList.add(lostAndFound.getCreated_at().toString());

                            //create user
                            User user = dog.getUser();
                            FBnameList.add(user.getFb_name());
                            FBpicList.add(user.getFb_profile_image());
                        }
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


                    ListView list = (ListView) findViewById(R.id.searchDogListView);
                    CustomAdapterSearch cus = new CustomAdapterSearch(context, itemName, itemBreed, itemNote,
                            itemPic, itemsDate, itemsPicFB, itemsNameFB);
                    list.setAdapter(cus);

                    //When Clicked
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long arg3) {
                            // TODO Auto-generated method stub
                            //Toast.makeText(context, "row : " + position, Toast.LENGTH_SHORT).show();

                            /**
                             * Send position for showing in Dog detail on next page (ProfileFragment)
                             */

                            //Intent myIntent = new Intent(context, FoundPostDetail.class);
                            //Cache.getInstance().put("lostAndFound", lostAndFounds[position]);
                            //startActivity(myIntent);
                        }

                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }
}




