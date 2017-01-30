package com.siriporn.dogfindertest.RESTServices.Interface;

import com.siriporn.dogfindertest.Models.ResponseFormat;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by siriporn on 19/1/2560.
 */

public interface FileService {
    @Multipart
    @POST("/server/upload/file")
    Call<ResponseFormat> uploadFile(@Part("name") String name, @Part MultipartBody.Part file);

    @POST("/server/upload/image")
    @Multipart
    Call<ResponseFormat> uploadImage(@Part("name") String name, @Part MultipartBody.Part file);
}
