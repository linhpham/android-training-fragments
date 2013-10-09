package jtribe.training.activity;

import jtribe.training.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class StockListActivity extends FragmentActivity implements
		OnStockItemSelectedListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_list_activity);

	}

	@Override
	public void onStockItemSelected(Uri uri) {
		StockWebviewFragment webview = (StockWebviewFragment) getSupportFragmentManager()
				.findFragmentById(R.id.stock_webview_fragment);
		if (webview == null || !webview.isInLayout()) {
			webview = new StockWebviewFragment();
			Bundle args = new Bundle();
			args.putString(StockWebviewFragment.LOAD_URL, uri.toString());
			webview.setArguments(args);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.anim.in_right,
					R.anim.out_right, R.anim.in_right, R.anim.out_right);
			fragmentTransaction.add(R.id.fragment_container, webview,
					"stockWebview");
			fragmentTransaction.addToBackStack("webview");
			fragmentTransaction.commit();
		} else {
			webview.loadUrl(uri.toString());
		}
	}
}
