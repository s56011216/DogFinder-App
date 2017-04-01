package com.siriporn.dogfindertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.siriporn.dogfindertest.MainActivity.context;

/**
 * Created by Moobi on 13/3/2560.
 */

public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            final String targetActivity = remoteMessage.getNotification().getClickAction();
//            RemoteMessage.Notification notification = remoteMessage.getNotification().;

            MainActivity.showDialog(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (targetActivity.equals("FoundPostDetail")) {
                        Intent intent = new Intent(DogFinderApplication.getContext(), FoundPostDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        DogFinderApplication.getContext().startActivity(intent);
                    }
                }
            }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


}
