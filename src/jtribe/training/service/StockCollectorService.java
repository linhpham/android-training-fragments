package jtribe.training.service;

import java.util.Timer;
import java.util.TimerTask;

import jtribe.training.task.FetchStockPrice;
import jtribe.training.task.GenerateNotification;

import android.app.Service;
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

			//If your having troubles after kicking off the service, you can enable the debugger via the code statement below 
			//android.os.Debug.waitForDebugger();
			
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
}
