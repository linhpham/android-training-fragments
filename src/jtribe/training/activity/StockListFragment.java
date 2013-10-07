package jtribe.training.activity;

import jtribe.training.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StockListFragment extends ListFragment {
	private OnStockItemSelectedListener stockItemSelectedListener;

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
		if (stockItemSelectedListener != null) {
			stockItemSelectedListener.onStockItemSelected(Uri.parse(url));
		} else {
			Log.e(this.getClass().getCanonicalName(), getActivity().toString() + " must implement OnStockItemSelectedListener");
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			stockItemSelectedListener = (OnStockItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnStockItemSelectedListener");
		}
	}
}
