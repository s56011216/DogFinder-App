package com.siriporn.dogfindertest.RESTServices.Interface;

import com.siriporn.dogfindertest.Models.Dog;

import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Moobi on 12-Jan-17.
 */

public interface DogService {

    @POST("/server/dog/")
    Call<Map<String, Object>> newDog(@Body Dog dog);

    @POST("/server/upload/")
    Call<Map<String, Object>> addImage(@Header("Content-type") String contentType, @Query("dog") Dog dog, @Query("image_id") int image_id);

}
