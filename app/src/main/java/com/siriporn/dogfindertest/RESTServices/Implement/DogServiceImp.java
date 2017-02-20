package com.siriporn.dogfindertest.RESTServices.Implement;

import android.util.Log;

import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.Models.Coordinate;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import okhttp3.MultipartBody;
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

    private DogServiceImp(){}

    public static DogServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter();
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
                Log.e("error", t.getMessage());
            }
        });
    }

    public void getAllMyDogs(Callback callback){
        Call call = service.getAllMyDog();
        call.enqueue(callback);
    }

    public void createLostAndFound(LostAndFound lostAndFound, Callback callback){
        Call call = service.createLostAndFound(lostAndFound);
        call.enqueue(callback);
    }

    public void getDog(int id, Callback callback){
        Call call = service.getDog(id);
        call.enqueue(callback);
    }

    public void getAllLostAndFound(Callback callback){
        Call call = service.getAllLostAndFound();
        call.enqueue(callback);
    }

    public void addCoordinate(Dog dog, Coordinate coordinate, Callback callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("dog", dog);
        map.put("coordinate", coordinate);
        Call call = service.addCoordinate(map);
        call.enqueue(callback);
    }

    public Response<ResponseFormat> getSimilarDogFound(String name, MultipartBody.Part body) throws IOException {
        Call<ResponseFormat> call = service.getSimilarDogFound(name, body);
        return call.execute();
    }

    public void getSimilarDogFound(String name, MultipartBody.Part body, Callback callback) {
        Call<ResponseFormat> call = service.getSimilarDogFound(name, body);
        call.enqueue(callback);
    }

    public Response<ResponseFormat> getSimilarDogFound(Dog dog) throws IOException {
        Call<ResponseFormat> call = service.getSimilarDogFound(dog.getId());
        return call.execute();
    }

    public void getSimilarDogFound(Dog dog, Callback callback) {
        Call<ResponseFormat> call = service.getSimilarDogFound(dog.getId());
        call.enqueue(callback);
    }
}
