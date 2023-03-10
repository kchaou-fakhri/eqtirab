package com.megahed.eqtarebmenalla.feature_data.presentation.ui.home;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SendDataService extends Service {
        private final LocalBinder mBinder = new LocalBinder();
        protected Handler handler;
        protected Toast mToast;

        public class LocalBinder extends Binder {
            public SendDataService getService() {
                return SendDataService .this;
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        @Override
        public void onCreate() {
            super.onCreate();

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {

                    TimerTask task = new TimerTask() {
                        public void run() {
                            Log.e("NIlu_TAG","Hello World");

                        }
                    };
                    Timer timer = new Timer("Timer");

                    long delay = 1000L;

                }
            });
            return android.app.Service.START_STICKY;
        }
}
