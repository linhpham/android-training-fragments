package jtribe.training.activity;

import jtribe.training.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class StockWebviewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_webview_layout);
 
        Intent intent = getIntent();
        String urlToLoad = intent.getData().toString();
 
        WebView webview = (WebView) findViewById(R.id.stockWebview);
        webview.loadUrl(urlToLoad);
    }
}
