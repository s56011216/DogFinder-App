package com.siriporn.dogfindertest.RESTServices.Implement;

import android.util.Log;

import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Moobi on 12-Jan-17.
 */

public class DogServiceImp {
    private static Retrofit retrofit;
    private static DogService service;
    private static DogServiceImp dogService;
    public static final String BASE_URL = "http://161.246.6.240:10100";

    private DogServiceImp(){};

    public static DogServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter(BASE_URL);
            service = retrofit.create(DogService.class);
            dogService = new DogServiceImp();
        }

        return dogService;
    }

    public void newDog(Dog dog, Callback callback) {
        Call call = service.newDog(dog);
        call.enqueue(callback);
    }

    public void uploadImage(final Dog dog, File file, final Callback callback) {
        FileServiceImp.getInstance().uploadImage(null, file, new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()) {
                    Map<String, Object> payload = response.body().getPayload();
                    Map<String, Object> data = new Hashtable<>();
                    data.put("dog", dog);
                    data.put("image_id", Converter.toInteger(payload.get("image_id").toString()));
                    Call addImageCall = service.addImage(data);
                    addImageCall.enqueue(callback);
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    public void getAllMyDogs(Callback callback){
        Call call = service.getAllMyDog();
        call.enqueue(callback);
    }
}
