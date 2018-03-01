package com.oudmon.dualprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;

public class RemoteService extends Service {


  private MyBinder mBinder;

  public RemoteService() {
    mBinder = new MyBinder();
  }

  @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    bindService(new Intent(this, LocalService.class), mConnection, BIND_IMPORTANT);
/*    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
        .setSmallIcon(android.R.drawable.ic_btn_speak_now)
        .setContentTitle("Remote Service")
        .setContentText("Hello World!");
    startForeground(9527, mBuilder.build());*/
    return START_STICKY;
  }

  private class MyBinder extends IMyAidlInterface.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
        String aString) throws RemoteException {

    }
  }

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override public void onServiceDisconnected(ComponentName name) {
      startService(new Intent(RemoteService.this, LocalService.class));
      bindService(new Intent(RemoteService.this, LocalService.class), mConnection, BIND_IMPORTANT);
    }
  };
}
