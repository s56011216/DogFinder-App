package com.siriporn.dogfindertest.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.siriporn.dogfindertest.Cache;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.DogFinderApplication;
import com.siriporn.dogfindertest.MainActivity;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.UserServiceImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;
import static android.R.attr.name;
import static android.R.attr.tag;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {



    private TextView mTextDetails;



    private String id, firstname, lastname, name, email, accessToken, pic_uri;
    private Date birth_date, token_exp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupTokenTracker() {

    }

    private void setupProfileTracker() {

    }

//     @Override
//    public void onViewCreated(View view, Bundle savedInstanceState){
//
//     }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityResult(int requestCode,int resultCode ,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

    }

    private void setupTextDetails(View view) {

    }

    private void setupLoginButton(View view) {

    }





}
