package com.example.mainactivity.lojista;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mainactivity.R;
public class AlarmePremiumReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        Intent intentApp = new Intent(context, PainelLojistaActivity.class);
        intentApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentApp, PendingIntent.FLAG_IMMUTABLE);

        //notificacao propriamente dita
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CANAL_PREMIUM_VVV")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Destaque a sua Vitrine! ")
                .setContentText("Faça o upgrade para o Premium e alcance ainda mais clientes!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);//desaparece ao ser clicada

        //segurança
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1001, builder.build());
        }
    }
}
