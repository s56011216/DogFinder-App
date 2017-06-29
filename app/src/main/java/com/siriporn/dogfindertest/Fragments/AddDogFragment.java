package com.siriporn.dogfindertest.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.siriporn.dogfindertest.CustomAdapter.ListAddDogImgAdapter;
import com.siriporn.dogfindertest.R;

import java.util.Calendar;


public class AddDogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView listImgDog;
    ListAddDogImgAdapter listAddDogImgAdapter;
    LinearLayoutManager linearLayoutManager;

    EditText name_input;
    Spinner breed_input;
    Spinner gender_input;
    EditText birthday_input;
    EditText age_input;
    EditText microship_input;
    Spinner bloodtype_input;
    Button add_dog_button;

    ArrayAdapter<String> breed_spinner_adapter;
    ArrayAdapter<String> gender_spinner_adapter;
    ArrayAdapter<String> bloodtype_spinner_adapter;


    public AddDogFragment() {
        // Required empty public constructor
    }


    public static AddDogFragment newInstance(String param1, String param2) {
        AddDogFragment fragment = new AddDogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_dog, container, false);

        //init add img recyclerview
        listImgDog = (RecyclerView) view.findViewById(R.id.list_add_dog_img);
        listAddDogImgAdapter = new ListAddDogImgAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        listImgDog.setLayoutManager(linearLayoutManager);
        listImgDog.setAdapter(listAddDogImgAdapter);


        name_input = (EditText) view.findViewById(R.id.add_dog_name);

        breed_input = (Spinner) view.findViewById(R.id.add_dog_breed);
        breed_spinner_adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, getResources().getStringArray(R.array.fragment_add_dog_breed_type));
        breed_input.setAdapter(breed_spinner_adapter);

        gender_input = (Spinner) view.findViewById(R.id.add_dog_gender);
        gender_spinner_adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, getResources().getStringArray(R.array.fragment_add_dog_gender_type));
        gender_input.setAdapter(gender_spinner_adapter);

        birthday_input = (EditText) view.findViewById(R.id.add_dog_birthday);
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday_input.setText("" + dayOfMonth + "/" + month + "/" + year);
            }
        };
        birthday_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity()
                        , date
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        birthday_input.setKeyListener(null);

        age_input = (EditText) view.findViewById(R.id.add_dog_age);

        microship_input = (EditText) view.findViewById(R.id.add_dog_microship);

        bloodtype_input = (Spinner) view.findViewById(R.id.add_dog_bloodtype);
        bloodtype_spinner_adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, getResources().getStringArray(R.array.fragment_add_dog_bloodtype_type));
        bloodtype_input.setAdapter(bloodtype_spinner_adapter);

        add_dog_button = (Button) view.findViewById(R.id.add_dog_add);
        add_dog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//
//
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
