package com.siriporn.dogfindertest.RESTServices.Implement;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public void uploadFile() {
        File file = null;
        try {
            file = File.createTempFile("hello", ".hi");
            file.deleteOnExit();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("hiiiiiii!! สวัสกัจ้า");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), requestFile);
        String descriptionString = "this is fine!!";
        //RequestBody description = RequestBody.create(MediaType.parse("text/plain"), descriptionString);
        Dog dog = new Dog();
        dog.setAge(5);
        dog.setName("sugust");
        Call call = service.uploadFile("sugust", body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("hello", "file is coming");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("hello", "problems");
            }
        });
    }
}
