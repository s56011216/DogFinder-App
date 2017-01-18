package com.siriporn.dogfindertest.RESTServices.Interface;

import com.siriporn.dogfindertest.Models.User;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Moobi on 10-Jan-17.
 */

public interface UserService {
    @POST("/server/user/login")
    Call<Map<String,Object>> login(@Header("Content-Type") String contentType, @Body User user);

    @GET("/server/user/is_authenticate")
    Call<Map<String,Object>> isAuthenticate();

    @GET("/server/user/self_info")
    Call<Map<String,Object>> getSelfInfo();

    @PUT("/server/user/self_info")
    Call<Map<String,Object>> updateSelfInfo(@Header("Content-Type") String contentType, @Body User user);
}
