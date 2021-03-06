package com.siriporn.dogfindertest.RESTServices.Interface;

import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<ResponseFormat> newDog(@Body Dog dog);

    @GET("/server/dog/")
    Call<ResponseFormat> getDog(@Query("id") int id);

    @POST("/server/dog/instance")
    Call<ResponseFormat> addImage(@Body Map<String, Object> map);

    @GET("/server/dog/get_all_dogs")
    Call<ResponseFormat> getAllMyDog(@Query("page") int page, @Query("limit") int limit);

    @Multipart
    @POST("/server/dog/find_similar_dogs")
    Call<ResponseFormat> getSimilarDogFound(@Part("name") String name, @Part MultipartBody.Part file);

    @GET("/server/dog/find_similar_dogs")
    Call<ResponseFormat> getSimilarDogFound(@Query("dog_id") int dogId);

    @POST("/server/dog/lost_and_found")
    Call<ResponseFormat> createLostAndFound(@Body LostAndFound lostAndFound);

    @GET("/server/dog/lost_and_found")
    Call<ResponseFormat> getAllLostAndFound(@Query("type") int type, @Query("max") int max);

    @POST("/server/dog/coordinate")
    Call<ResponseFormat> addCoordinate(@Body Map<String, Object> map);

    @GET("/server/dog/last_noti")
    Call<ResponseFormat> getLastNotification();
}
