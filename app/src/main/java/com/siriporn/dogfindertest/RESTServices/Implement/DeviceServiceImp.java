package com.siriporn.dogfindertest.RESTServices.Implement;

import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.DeviceService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Moobi on 13/3/2560.
 */

public class DeviceServiceImp {

    private static Retrofit retrofit;
    private static DeviceService service;
    private static DeviceServiceImp deviceService;

    private DeviceServiceImp(){}

    public static DeviceServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter();
            service = retrofit.create(DeviceService.class);
            deviceService = new DeviceServiceImp();
        }

        return deviceService;
    }

    public ResponseFormat updateFCMToken(String fcmToken) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("registration_id", fcmToken);
        Call call = service.updateFCMToken(map);
        return Converter.toPOJO(call.execute().body(), ResponseFormat.class);
    }

    public void updateFCMToken(String fcmToken, Callback callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("registration_id", fcmToken);
        Call call = service.updateFCMToken(map);
        call.enqueue(callback);
    }
}
