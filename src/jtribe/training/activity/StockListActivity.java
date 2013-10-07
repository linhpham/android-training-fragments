package jtribe.training.activity;

import jtribe.training.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class StockListActivity extends FragmentActivity implements OnStockItemSelectedListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_list_activity);
	}

	@Override
	public void onStockItemSelected(Uri uri) {
		StockWebviewFragment webview = (StockWebviewFragment) getSupportFragmentManager().findFragmentById(R.id.stock_webview_fragment);
		if (webview == null || !webview.isInLayout()) {
			Intent intent = new Intent(getApplicationContext(), StockWebviewActivity.class);
			 intent.setData(uri);
			 startActivity(intent);
		} else {
			webview.loadUrl(uri.toString());
		}
	}
}
