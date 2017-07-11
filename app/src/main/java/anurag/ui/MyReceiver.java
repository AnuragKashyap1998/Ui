package anurag.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    public static int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notification=new NotificationCompat.Builder(context).setSmallIcon(android.R.drawable.alert_light_frame)
                .setContentTitle("ALARM")
                .setAutoCancel(true).setContentTitle("Notes");
        Intent intent1=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=(PendingIntent) PendingIntent.getActivity(context,1,intent1,0);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i,notification.build());
        i++;
    }
}
