package com.siriporn.dogfindertest;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class LostPostAcitivity extends AppCompatActivity {

    Dog dog = new Dog();
    User user = new User();
    LostAndFound lostAndFound = new LostAndFound();
    private String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_post_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Profile profile = Profile.getCurrentProfile();
        ImageView picture = (ImageView) findViewById(R.id.lostUserPicWritePost);
        TextView name = (TextView) findViewById(R.id.nameFB);
        TextView note = (TextView) findViewById(R.id.noticeLostPost);
        final ImageView search = (ImageView) findViewById(R.id.searchMydog);
        final TextView show_name = (TextView) findViewById(R.id.nameLostPost);

        //show pic and FB name
        name.setText(profile.getName());
        Uri picUri = profile.getProfilePictureUri(200, 200);
        Glide.with(context)
                .load(picUri)
                .override(100, 100)
                .centerCrop()
                .into(picture);

        //get note
        String notes = note.getText().toString();

        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    ArrayList<String> stockList = new ArrayList<>();

                    Dog[] dogs = Converter.toPOJO(response.body().getPayload().get("dogs"), Dog[].class);

                    for(Dog dog: dogs) {
                        stockList.add(dog.getName());
                    }



                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Creating the instance of PopupMenu
                            PopupMenu popup = new PopupMenu(LostPostAcitivity.this, search);
                            //popup.getMenu().add();
                            //Inflating the Popup using xml file
                            popup.getMenuInflater()
                                    .inflate(R.menu.popup_menu, popup.getMenu());

                            //registering popup with OnMenuItemClickListener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    Toast.makeText(
                                            LostPostAcitivity.this,
                                            "You Clicked : " + item.getTitle(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    show_name.setText(item.toString());
                                    return true;
                                }
                            });

                            popup.show(); //showing popup menu
                        }
                    }); //closing the setOnClickListener method

                }else{
                    Log.e("Sucess","Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("Sucess","onFailure");
            }
        });
    }

    public void writePostLostClicked(View view) {

        EditText noticeText = (EditText) findViewById(R.id.noticeLostPost);
        note = noticeText.getText().toString();

        user.getId();
        lostAndFound.setType(LostAndFound.LOST);
        lostAndFound.setDog(dog);
        //lostAndFound.setUser(user);
        lostAndFound.setNote(note);
    }
}
