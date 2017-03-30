package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Moobi on 10-Jan-17.
 */

public class NetworkConfigs {
    public static final String BASE_URL = "http://161.246.6.240:10100";
    public static final String DEBUG_BASE_URL = "http://161.246.6.240:10200";
    public static final String DEBUG_BASE_URL2 = "http://192.168.31.208:10200";

    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Context context = DogFinderApplication.getContext();
                SharedPreferences sp = context.getSharedPreferences("USER_DATA", context.MODE_PRIVATE);
                String token = sp.getString("token", null);
                if(token == null)
                    return chain.proceed(originalRequest);

                Request authorisedRequest = originalRequest.newBuilder()
                        .header("Authorization", "Token " + token)
                        .build();
                return chain.proceed(authorisedRequest);
            }
        }).readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();

        return client;
    }

    public static Retrofit getRestAdapter() {
        return new Retrofit.Builder().baseUrl(DEBUG_BASE_URL2).client(NetworkConfigs.getClient()).addConverterFactory(GsonConverterFactory.create((new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())).build();
    }

}
