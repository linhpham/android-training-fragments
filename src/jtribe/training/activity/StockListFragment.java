package jtribe.training.activity;

import jtribe.training.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StockListFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.stock_codes,
				R.layout.stock_list_item));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String[] linksArray = getResources().getStringArray(R.array.stock_links);

		String url = linksArray[position];
		Intent intent = new Intent(getActivity().getApplicationContext(), StockWebviewActivity.class);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
}
