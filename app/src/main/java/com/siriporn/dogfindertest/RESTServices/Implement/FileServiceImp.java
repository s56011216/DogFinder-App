package com.siriporn.dogfindertest.RESTServices.Implement;

import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.FileService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by siriporn on 19/1/2560.
 */

public class FileServiceImp {
    private static Retrofit retrofit;
    private static FileService service;
    private static FileServiceImp fileService;

    private FileServiceImp(){}

    public static FileServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter();
            service = retrofit.create(FileService.class);
            fileService = new FileServiceImp();
        }

        return fileService;
    }

    public void uploadFile(String name, File file, Callback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), requestFile);
        Call call = service.uploadFile(name, body);
        call.enqueue(callback);
    }

    public void uploadImage(String name, File file, Callback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), requestFile);
        Call call = service.uploadImage(name, body);
        call.enqueue(callback);
    }
}
