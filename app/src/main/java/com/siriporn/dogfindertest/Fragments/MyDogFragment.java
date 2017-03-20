package com.siriporn.dogfindertest.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.MyDogDetail;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.util.Log.e;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by siriporn on 26/12/2559.
 */

public class MyDogFragment extends Fragment {
    private String[] itemsPic;
    private View myView;
    public View ftView;
    private ListView list;
    private CustomAdapterDog cus;
    public Handler mHandler;
    public boolean isLoading = false;
    public int currentId=11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_mydog, container, false);
        Items();
        return myView;

    }
    List<Dog> stockList;
    public void Items(){
        /**
         * my dog
         */
        int i = 1;

            DogServiceImp.getInstance().getAllMyDogs(i, 6, new Callback<ResponseFormat>() {
                @Override
                public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                    if (response.body().isSuccess()) {
                        Log.i("Success", "OK");
                        stockList = new ArrayList<>();
                        ArrayList<Double> latList = new ArrayList<>();
                        ArrayList<Double> longList = new ArrayList<>();
                        ArrayList<String> picList = new ArrayList<>();
                        final Dog[] dogs = Converter.toPOJO(response.body().getPayload().get("dogs"), Dog[].class);

                        for (Dog dog : dogs) {
                            stockList.add(new Dog(dog.getId(), dog.getAge(), dog.getLatitude(), dog.getLongitude(),dog.getUser()
                                    , dog.getName(), dog.getBreed(), dog.getNote(), dog.getImages(), dog.getCreated_at()
                                    , dog.getUpdated_at()));

                            latList.add(dog.getLatitude());
                            longList.add(dog.getLongitude());
                            picList.add(dog.getImages()[0]);
                                }
                                //convert double list to double[]
                                final double[] lat_list = new double[latList.size()];
                                for (int i = 0; i < lat_list.length; i++) {
                                    lat_list[i] = latList.get(i).doubleValue();
                                }
                                final double[] long_list = new double[longList.size()];
                                for (int i = 0; i < long_list.length; i++) {
                                    long_list[i] = longList.get(i).doubleValue();
                                }

                            // URI convert List<String> to String[]
                            itemsPic = new String[picList.size()];
                            itemsPic = picList.toArray(itemsPic);
                        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        ftView = li.inflate(R.layout.footer_view, null);
                        mHandler = new MyHandler();

                        list = (ListView) myView.findViewById(R.id.dogListView);
                        cus = new CustomAdapterDog(getActivity().getApplicationContext(), stockList);
                        list.setAdapter(cus);


                        //When Clicked
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                    long arg3) {
                                // TODO Auto-generated method stub
//                            Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();

                                /**
                                 * Send position for showing in Dog detail on next page (MyDogDetail)
                                 */
                                String positions = Integer.toString(position);
                                Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                                myIntent.putExtra("SelectRowDog", positions);
                                myIntent.putExtra("Pic", itemsPic);
                                myIntent.putExtra("lat", lat_list);
                                myIntent.putExtra("lon", long_list);


                                startActivity(myIntent);
                            }

                        });

                        list.setOnScrollListener(new AbsListView.OnScrollListener() {

                            public void onScrollStateChanged(AbsListView view, int scrollState) {


                            }

                            public void onScroll(AbsListView view, int firstVisibleItem,
                                                 int visibleItemCount, int totalItemCount) {

                                int rowsOnScreen =  Math.abs(view.getLastVisiblePosition());
                                int aa = Math.abs(list.getCount());

                                //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                                if(view.getLastVisiblePosition() == totalItemCount-1 && list.getCount() >= 5 && isLoading == false) {
                                        isLoading = true;
                                    Thread thread = new ThreadGetMoreData();
                                    //Start thread
                                    thread.start();
                                }
                            }
                        });

                    } else {
                        e("Sucess", "Failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseFormat> call, Throwable t) {
                    Log.e("Sucess", "onFailure");
                }
            });
        //return i;
        }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //Add loading view during search processing
                    list.addFooterView(myView);
                    break;
                case 1:
                    //Update data adapter and UI
                    cus.addListItemToAdapter((ArrayList<Dog>) msg.obj);
                    //Remove loading view after update listview
                    list.removeFooterView(myView);
                    isLoading = false;

                    break;
                default:
                    break;
            }
        }
    }

    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            //Add footer view after get data
            mHandler.sendEmptyMessage(0);
            //Search more data
            List<Dog> lstResult = getMoreData();
            //Delay time to show loading footer when debug, remove it when release
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send the result to Handle
            Message msg = mHandler.obtainMessage(1, lstResult);
            mHandler.sendMessage(msg);

        }
    }
    int i = 2;
   List<Dog> addList;
    private List<Dog> getMoreData() {
        // new data
        addList = new ArrayList<>();
        //User user = new User();

        DogServiceImp.getInstance().getAllMyDogs(i, 1, new Callback<ResponseFormat>() {

            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if (response.body().isSuccess()) {
                    i++;
                    Log.i("Success", "OK");
                    //List<Dog> addList = new ArrayList<>();
                    final Dog[] dogs = Converter.toPOJO(response.body().getPayload().get("dogs"), Dog[].class);
                    for (Dog dog : dogs) {
                        stockList.add(new Dog(dog.getId(), dog.getAge(), dog.getLatitude(), dog.getLongitude(), dog.getUser(), dog.getName(),
                                dog.getBreed(), dog.getNote(), dog.getImages(), dog.getCreated_at(), dog.getUpdated_at()));
                        addList.add(new Dog(dog.getId(), dog.getAge(), dog.getLatitude(), dog.getLongitude(), dog.getUser(), dog.getName(),
                                dog.getBreed(), dog.getNote(), dog.getImages(), dog.getCreated_at(), dog.getUpdated_at()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("Sucess", "onFailure");
            }
        });

        return addList;
    }
}

