package com.siriporn.dogfindertest.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.DogFinderApplication;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.UserServiceImp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

/**
 * Created by siriporn on 26/12/2559.
 */

public class ProfileFragment extends Fragment {
    View myviewuser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        myviewuser = lf.inflate(R.layout.activity_user_detail, container, false);

        /*Profile profile = Profile.getCurrentProfile();

        ImageView picture = (ImageView) myviewuser.findViewById(R.id.imageUser);
        TextView name = (TextView) myviewuser.findViewById(R.id.nameUser);

        name.setText(profile.getName());
        Uri picUri = profile.getProfilePictureUri(200,200);
        Glide.with(context)
                .load(picUri)
                .override(200, 200)
                .centerCrop()
                .into(picture);
*/
        final User user = DogFinderApplication.getUser();

        ImageView picture = (ImageView) myviewuser.findViewById(R.id.imageUser);
        TextView name = (TextView) myviewuser.findViewById(R.id.nameUser);
        TextView email = (TextView) myviewuser.findViewById(R.id.email_user);
        TextView phone = (TextView) myviewuser.findViewById(R.id.phone_user);
        //TextView birth = (TextView) myviewuser.findViewById(R.id.birth_user);
        //TextView last = (TextView) myviewuser.findViewById(R.id.lastLogin_user);

        name.setText(user.getFb_name());
        email.setText(user.getEmail());
        phone.setText(user.getTelephone());
        //birth.setText(user.getBirth_date().toString());
        //last.setText(user.getLast_login().toString());


        String picUri = user.getFb_profile_image().toString();
        Glide.with(context)
                .load(picUri)
                .override(200, 200)
                .centerCrop()
                .into(picture);

        return myviewuser;
    }
}

