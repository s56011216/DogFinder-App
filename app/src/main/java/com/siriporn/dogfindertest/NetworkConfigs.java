package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Moobi on 10-Jan-17.
 */

public class NetworkConfigs {

    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Context context = MainActivity.getContext();
                SharedPreferences sp = context.getSharedPreferences("USER_DATA", context.MODE_PRIVATE);
                String token = sp.getString("token", null);
                if(token == null)
                    return chain.proceed(originalRequest);

                Request authorisedRequest = originalRequest.newBuilder()
                        .header("Authorization", "Token " + token)
                        .build();
                return chain.proceed(authorisedRequest);
            }
        }).build();

        return client;
    }
}
