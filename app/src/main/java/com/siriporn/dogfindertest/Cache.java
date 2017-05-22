package com.siriporn.dogfindertest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by siriporn on 13/2/2560.
 */

public class Cache {

    private static Cache instance;
    private static Map<String, Object> cache;
    private static Map<String, Class<?>> objectClass;

    private Cache() {}

    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
            cache = new HashMap<>();
            objectClass = new HashMap<>();
        }
        return instance;
    }

    public void put(String key, Object object) {
        objectClass.put(key, object.getClass());
        cache.put(key, object);
    }

//    public Object get(String key) {
//        return cache.get(key);
//    }

    public <T> T get(String key) {
        try {
            return (T) objectClass.get(key).cast(cache.get(key));
        } catch (Exception ex) {
            return null;
        }
    }
}
