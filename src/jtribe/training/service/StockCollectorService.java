package jtribe.training.service;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import jtribe.training.task.FetchStockPrice;
import jtribe.training.task.GenerateNotification;
import jtribe.training.utils.StockPriceStore;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class StockCollectorService extends Service {
	private static final String TAG = "StockCollectorService";

	private static final String STOCK_PRICE = "stock_price";
	protected static final String PREFS_NAME = "stock_prefs";

	protected static final boolean mSilentMode = false;
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

			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			StockPriceStore s = StockPriceStore.getInstance();
			// if stock price is different to last known
			if (!latestStockPrice.equalsIgnoreCase(settings.getString(
					STOCK_PRICE, ""))) {
				// notify
				GenerateNotification gn = new GenerateNotification();
				gn.createNotification(latestStockPrice, getApplicationContext());
				
				// keep track of changes in stock price
				long timestamp = Calendar.getInstance().getTimeInMillis();
				StockPriceStore.getInstance()
						.set(String.valueOf(timestamp), latestStockPrice)
						.save();
				
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(STOCK_PRICE, latestStockPrice);
				// Commit the edits!
				editor.commit();
			}
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
				NotificationManager nMgr = (NotificationManager) this
						.getSystemService(ns);
				nMgr.cancel(notificationId);
				stopSelf();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
