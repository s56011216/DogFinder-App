package com.siriporn.dogfindertest.Fragments;

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
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.R;

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

        Profile profile = Profile.getCurrentProfile();
        User user = new User();

        ImageView picture = (ImageView)myviewuser.findViewById(R.id.imageUser);
        TextView name = (TextView) myviewuser.findViewById(R.id.nameUser);
        TextView email = (TextView) myviewuser.findViewById(R.id.emailUser);

        name.setText(profile.getName());


        Uri picUri = profile.getProfilePictureUri(200, 200);
        Glide.with(context)
                .load(picUri)
                .override(200, 200)
                .centerCrop()
                .into(picture);

        return myviewuser;
    }
}

