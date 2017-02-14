package com.siriporn.dogfindertest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by siriporn on 30/1/2560.
 */

public class Converter {
    public static Double toDouble(String text) {
        return Double.parseDouble(text);
    }

    public static Double toDouble(Integer number) {
        return number.doubleValue();
    }

    public static Integer toInteger(String text) {
        return toDouble(text).intValue();
    }

    public static Integer toInteger(Double number) {
        return number.intValue();
    }

    public static <T> T toPOJO(Object any, Class<T> classOfT) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return (T) gson.fromJson(gson.toJson(any), classOfT);
    }
}
