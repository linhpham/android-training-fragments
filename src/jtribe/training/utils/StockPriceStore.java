package jtribe.training.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.os.Environment;
import android.util.Log;

public class StockPriceStore {
	
	private static final String FILENAME = "stock_price_store";

	private static StockPriceStore mInstance;
	
	protected HashMap<String, String> stockPricesMap;

	// private constructor to have only one instance
	@SuppressWarnings("unchecked")
	private StockPriceStore() {
		// read file and initialize hash map
		File file = getFile();
		ObjectInputStream inputStream;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			stockPricesMap = (HashMap<String, String>) inputStream.readObject();
		} catch (Exception e) {
			Log.e(FILENAME, "can't read hash map", e);
			stockPricesMap = new HashMap<String, String>();
		}
	}
	
	public static StockPriceStore getInstance() {
		if (mInstance == null) {
			mInstance = new StockPriceStore();
		}
		return mInstance;
	}

	public String get(String key) {
		return stockPricesMap.get(key);
	}
	
	public StockPriceStore set(String key, String value) {
		stockPricesMap.put(key, value);
		save();
		return mInstance;
	}
	
	public void save() {
		File file = getFile();    
		ObjectOutputStream outputStream;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(stockPricesMap);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			Log.e(FILENAME, "error saving map", e);
		}
		
	}

	private File getFile() {
		return new File(Environment.getExternalStorageDirectory(), FILENAME);
	}
}
