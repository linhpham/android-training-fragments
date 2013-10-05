package jtribe.training.activity;

import jtribe.training.R;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class StockListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.stock_codes,
				R.layout.stock_list_item));

		getListView().setOnItemClickListener(stockListItemClicked);
	}

	private OnItemClickListener stockListItemClicked = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String[] linksArray = getResources().getStringArray(R.array.stock_links); 
			
			String url = linksArray[position];
			Intent intent = new Intent(getApplicationContext(), StockWebviewActivity.class);
			intent.setData(Uri.parse(url));
			startActivity(intent);
		}
	};

}
