package com.siriporn.dogfindertest.RESTServices.Interface;

import com.siriporn.dogfindertest.Models.ResponseFormat;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Moobi on 13/3/2560.
 */

public interface DeviceService {

    @POST("/server/device/fcm_token")
    Call<ResponseFormat> updateFCMToken(@Body Map<String, Object> map);
}
