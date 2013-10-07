package jtribe.training.activity;

import jtribe.training.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

public class StockWebviewActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_webview_fragment);
 
        Intent intent = getIntent();
        String urlToLoad = intent.getData().toString();
 
        WebView webview = (WebView) findViewById(R.id.stockWebview);
        webview.loadUrl(urlToLoad);
    }
}
