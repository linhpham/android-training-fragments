package jtribe.training.activity;

import jtribe.training.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class StockWebviewFragment extends Fragment {
	private WebView webview = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		webview = (WebView) inflater.inflate(R.layout.stock_webview_layout, container, false);
		return webview;
	}

	public void loadUrl(String newUrl) {
		if (webview != null) {
			webview.loadUrl(newUrl);
		}
	}
}
