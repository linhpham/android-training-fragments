package jtribe.training.task;

import jtribe.training.R;
import jtribe.training.activity.MainActivity;
import jtribe.training.service.WidgetProvider;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GenerateNotification {
	final int STOCK_NOTIFY_ID = 1;
	
	public void createNotification(String latestStockPrice, Context context) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		
		int icon = R.drawable.ic_stat_dollar;
		CharSequence tickerText = "Latest stock price: $" + latestStockPrice;
		long when = System.currentTimeMillis();
		
		Intent notificationIntent = new Intent(context, MainActivity.class);
		//Take particular note of FLAG_UPDATE_CURRENT which tells our pending intent to refresh, and inturn load up our new stock value. Without it our MainActivity would only update once.
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		Bundle bun = new Bundle();
		bun.putString("latestStockPrice", latestStockPrice);
		notificationIntent.putExtras(bun);
		
		
		NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context);
		notiBuilder.setTicker(tickerText);
		notiBuilder.setContentTitle("Stock update");
		notiBuilder.setContentText("Latest stock price: $" + latestStockPrice);
		notiBuilder.setStyle(new NotificationCompat.BigTextStyle()
			.bigText("Latest stock price: $" + latestStockPrice + " updated recently.")
		);
		notiBuilder.setWhen(when);
		notiBuilder.setSmallIcon(icon);
		notiBuilder.setContentIntent(contentIntent);
		
		
		Notification notification = notiBuilder.build();
		setNotificationProperties(notification);
		mNotificationManager.notify(STOCK_NOTIFY_ID, notification);
		AppWidgetManager gm = AppWidgetManager.getInstance(context);
		ComponentName widgetProvider = new ComponentName(context, WidgetProvider.class);
		int[] appWidgetIds = gm.getAppWidgetIds(widgetProvider);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			WidgetProvider.updateAppWidget(context, gm, appWidgetIds[i], latestStockPrice);
		}
	}

	private void setNotificationProperties(Notification notification) {
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		notification.defaults |= Notification.DEFAULT_SOUND;
		
		long[] vibrate = {0,100,200,300};
		notification.vibrate = vibrate;
		
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
	}
}
