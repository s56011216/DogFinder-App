package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by siriporn on 26/12/2559.
 */

public class MyDogFragment extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_mydog, container, false);
        final String[] items = new String[] { "Nancy", "ThongDee", "Boo",
                "Carrot" };

        ListView list = (ListView)myView.findViewById(R.id.dogListView);
        CustomAdapterDog cus = new CustomAdapterDog(getActivity(),items);
        list.setAdapter(cus);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                myIntent.putExtra("welkerij", position);
                startActivity(myIntent);

            }

        });

        return myView;

    }
}
