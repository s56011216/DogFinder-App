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

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class SearchBreed extends Fragment {

    public String breed_select = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.layout_mydog, container, false);

        ListView friendsListView = (ListView)myView.findViewById(R.id.breedsListView);

        final ArrayList<String> mySelect = new ArrayList<String>(asList
                        ("Airedale Terrier", "Akita", "Alaskan malamute", "Anatolian shepherd","Australian Cattle"
                        , "Beagle", "Chow", "Golden", "Italian Greyhound", "Maltese"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mySelect);

        friendsListView.setAdapter(arrayAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity().getApplicationContext(), "Select " + mySelect.get(position), Toast.LENGTH_SHORT).show();
                breed_select = mySelect.get(position);

            }
        });
        friendsListView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),DogAddInfoActivity.class);
                intent.putExtra("breed_select", breed_select);
                startActivity(intent);
            }
        });
        return myView;
    }
}
