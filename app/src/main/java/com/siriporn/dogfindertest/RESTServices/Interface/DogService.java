package com.siriporn.dogfindertest.RESTServices.Interface;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Moobi on 12-Jan-17.
 */

public interface DogService {
    @Multipart
    @POST("/server/upload/")
    Call<ResponseBody> uploadFile(@Part("name") String name, @Part MultipartBody.Part file);

}
