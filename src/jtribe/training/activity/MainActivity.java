package jtribe.training.activity;

import jtribe.training.R;
import jtribe.training.service.StockCollectorService;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private Button portfolioBtn;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		startService(new Intent(StockCollectorService.class.getName()));
		Bundle bun = getIntent().getExtras();
		String latestStockPrice = null;
		if (bun != null) {
			latestStockPrice = bun.getString("latestStockPrice");
			if (latestStockPrice != null) {
				TextView mainActivityTextView = (TextView) findViewById(R.id.mainActivityTextView);
				mainActivityTextView.setText("Latest stock price is $" + latestStockPrice);
			}
		}
		
		portfolioBtn = (Button) findViewById(R.id.portfolioBtn);
		portfolioBtn.setOnClickListener(loadStockListActivity);
		
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		portfolioBtn.startAnimation(fadeIn);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	private OnClickListener loadStockListActivity = new OnClickListener() {
		public void onClick(View v) {
			float distance = portfolioBtn.getTop() - getWindow().getDecorView().getHeight();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				ObjectAnimator obj = ObjectAnimator.ofFloat(portfolioBtn, "y", distance);
				obj.setDuration(700);
				obj.addListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animator animation) {
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {
						startMainActivity();
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
					}
				});
				
				obj.start();
			} else {
				startMainActivity();
			}
		}
	};

	protected void startMainActivity() {
		Intent i = new Intent(MainActivity.this, StockListActivity.class);
		startActivity(i);
		// important to call this after startActivity
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
	}
	
}