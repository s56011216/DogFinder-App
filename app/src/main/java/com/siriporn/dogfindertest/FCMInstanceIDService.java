package com.siriporn.dogfindertest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DeviceServiceImp;

import java.io.IOException;


/**
 * Created by Moobi on 11/3/2560.
 */

public class FCMInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        try {
            ResponseFormat response = DeviceServiceImp.getInstance().updateFCMToken(refreshedToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFCMToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }
}
