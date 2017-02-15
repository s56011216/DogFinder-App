package com.siriporn.dogfindertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class LostPostAcitivity extends AppCompatActivity {

    Dog dog = new Dog();
    private Dog chosenDog;
    Dog[] dogs;
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
                if (response.body().isSuccess()) {
                    final List<String> stockList = new ArrayList<>();
                    dogs = Converter.toPOJO(response.body().getPayload().get("dogs"), Dog[].class);

                    for (Dog dog : dogs) {
                        stockList.add(dog.getName());
                    }

                    final CharSequence[] charSequenceStockList = stockList.toArray(new CharSequence[stockList.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LostPostAcitivity.this);
                    builder.setTitle("Who's lost?");
                    builder.setItems(charSequenceStockList, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            show_name.setText(stockList.get(which));
                            for(Dog dog : dogs){
                                if(dog.getName() == stockList.get(which)){
                                    setChosenDog(dog);
                                    break;
                                }
                            }
                        }
                    });
                    builder.show();
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

    public void writeLostPostClicked(View view){

        EditText noticeText = (EditText) findViewById(R.id.noticeLostPost);
        note = noticeText.getText().toString();
        user.getId();
        lostAndFound.setType(LostAndFound.LOST);
        lostAndFound.setDog(getChosenDog());
        //lostAndFound.setUser(user);
        lostAndFound.setNote(note);

        DogServiceImp.getInstance().createLostAndFound(lostAndFound, new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if (response.body().isSuccess()) {
                    Log.e("onResponse","Success");
                }else{
                    Log.e("onResponse","notSuccess");
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("onFailure","createLostAndFound");
            }
        });

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void setChosenDog(Dog dog) {
        chosenDog = dog;
    }

    private Dog getChosenDog() {
        return chosenDog;
    }
}