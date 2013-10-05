package jtribe.training.service;

import jtribe.training.R;
import jtribe.training.activity.MainActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId, null);
		}
	}
	
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
			String latestStockPrice) {
		
		Intent notificationIntent = new Intent(context, MainActivity.class);
		Bundle bun = new Bundle();
		bun.putString("latestStockPrice", latestStockPrice);
		notificationIntent.putExtras(bun);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
		views.setOnClickPendingIntent(R.layout.appwidget_layout, contentIntent);

		if (latestStockPrice != null) {
			views.setTextViewText(R.id.widgetTextview, "Latest stock price $" + latestStockPrice);
		} else {
			views.setTextViewText(R.id.widgetTextview, "Fetching latest stock price");
		}
		
		// Tell the AppWidgetManager to perform an update on the current app widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
