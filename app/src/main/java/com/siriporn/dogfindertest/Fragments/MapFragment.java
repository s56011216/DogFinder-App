package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siriporn.dogfindertest.FindPostActivity;
import com.siriporn.dogfindertest.R;

/**
 * Created by siriporn on 26/12/2559.
 */

public class MapFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_map, container, false);
        Intent intent = new Intent(getActivity(), FindPostActivity.class);

        startActivity(intent);
        return myView;
    }
}
