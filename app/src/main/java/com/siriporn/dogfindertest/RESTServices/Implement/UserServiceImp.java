package com.siriporn.dogfindertest.RESTServices.Implement;

import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Moobi on 10-Jan-17.
 */

public class UserServiceImp {
    private static Retrofit retrofit;
    private static UserService service;
    private static UserServiceImp userService;
    public static final String BASE_URL = "http://161.246.6.240:10100";

    private UserServiceImp(){};

    public static UserServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter(BASE_URL);
            service = retrofit.create(UserService.class);
            userService = new UserServiceImp();
        }

        return userService;
    }

    public void login(User user, Callback callback) {
        Call call = service.login("application/json", user);
        call.enqueue(callback);
    }

    public void isAuthenticate(Callback callback) {
        Call call = service.isAuthenticate();
        call.enqueue(callback);
    }

    public void updateSelfInfo(User user, Callback callback) {
        Call call = service.updateSelfInfo("application/json", user);
        call.enqueue(callback);
    }
}
