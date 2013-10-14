package jtribe.training.activity;

import jtribe.training.R;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class StockWebviewFragment extends Fragment {
	public static final String LOAD_URL = "url";
	private WebView webview = null;
	private AnimationDrawable mLoadingAnimation;
	private View mAnimationContainer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.stock_webview_layout, container, false);
		webview = (WebView) v.findViewById(R.id.stockWebview);
		webview.setWebViewClient(new WebViewClient() {
		
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				mLoadingAnimation.start();
				mAnimationContainer.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mLoadingAnimation.stop();
				mAnimationContainer.setVisibility(View.GONE);
			}
		});
		
		ImageView imageView = (ImageView) v.findViewById(R.id.loading_anim);
		imageView.setBackgroundResource(R.drawable.loading_animation);
		mLoadingAnimation = (AnimationDrawable) imageView.getBackground();
		mAnimationContainer = v.findViewById(R.id.animationContainer);
		
		if (getArguments() != null && getArguments().containsKey(LOAD_URL)) {
			String url = getArguments().getString(LOAD_URL);
			if (url != null) {
				webview.loadUrl(url);
			}
		}
 		return v;
	}

	public void loadUrl(String newUrl) {
		if (webview != null) {
			webview.loadUrl(newUrl);
		}
	}
}
