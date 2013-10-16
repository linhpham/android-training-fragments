package jtribe.training.service;

import java.util.Timer;
import java.util.TimerTask;

import jtribe.training.task.FetchStockPrice;
import jtribe.training.task.GenerateNotification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StockCollectorService extends Service {
	private static final String TAG = "StockCollectorService";
	private Timer timer;

	private TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {
			Log.i(TAG, "Timer task doing work");

			// If your having troubles after kicking off the service, you can
			// enable the debugger via the code statement below
			// android.os.Debug.waitForDebugger();

			FetchStockPrice fsp = new FetchStockPrice();
			String latestStockPrice = fsp.getLatestStockPrice();
			Log.i(TAG, "Latest Stock Price: " + latestStockPrice);

			GenerateNotification gn = new GenerateNotification();
			gn.createNotification(latestStockPrice, getApplicationContext());
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Service creating");

		timer = new Timer("StockCollectorTimer");
		timer.schedule(updateTask, 1000L, 60 * 1000L);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Service destroying");

		timer.cancel();
		timer = null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null && intent.getAction() != null) {
			if ("stop".equals(intent.getAction())) {
				int notificationId = intent.getIntExtra("notificationId", -1);
				String ns = Context.NOTIFICATION_SERVICE;
			    NotificationManager nMgr = (NotificationManager) this.getSystemService(ns);
			    nMgr.cancel(notificationId);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// do stop service as can't do this in the service
						stopSelf();
					}
				}, "StopServiceThread");
				thread.start();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
