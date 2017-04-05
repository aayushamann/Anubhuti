package in.anbti.anubhuti;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String st = remoteMessage.getNotification().getBody();
//        display(st);
    }

//    public void display(String st) {
//        TextView tv = (TextView) ((Activity)getBaseContext()).findViewById(R.id.notification_message);
//        tv.setText(st);
//    }
}
