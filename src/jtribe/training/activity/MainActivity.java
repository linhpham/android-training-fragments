package jtribe.training.activity;

import jtribe.training.R;
import jtribe.training.service.StockCollectorService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
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
		
		Button portfolioBtn = (Button) findViewById(R.id.portfolioBtn);
		portfolioBtn.setOnClickListener(loadStockListActivity);
	}
	
	private OnClickListener loadStockListActivity = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, StockListActivity.class);
			startActivity(i);
		}
	};
}