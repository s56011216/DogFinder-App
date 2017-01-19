package com.siriporn.dogfindertest.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterFind;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.SameDogActivity;

/**
 * Created by siriporn on 26/12/2559.
 */

public class FindFragment extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_find, container, false);

        final String[] items = new String[] { "Boo", "ThongDee", "ThongDee",
                "ThongDee" };

        ListView list = (ListView)myView.findViewById(R.id.findListView);
        CustomAdapterFind cus = new CustomAdapterFind(getActivity(),items);
        list.setAdapter(cus);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(getActivity(), SameDogActivity.class);
                startActivity(myIntent);

            }

        });

        return myView;
    }

}
