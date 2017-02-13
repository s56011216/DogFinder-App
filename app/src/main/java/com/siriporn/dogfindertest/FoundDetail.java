package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.siriporn.dogfindertest.Models.LostAndFound;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class FoundDetail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.activity_found_detail, container, false);
        Intent myIntent = new Intent();
        LostAndFound lostAndFound = (LostAndFound) Cache.getInstance().get("lostAndFound");
        lostAndFound.getDog().getId();
//        myIntent.getParcelableExtra("lost_and_founds");
        return myView;
    }
}
