package com.yongmo.androidnetworksample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String DEBUG_TAG = "NetworkStatusExample";
	
	TextView tv = null;
	
	boolean isWifiConn = false;
	boolean isMobileConn = false;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)findViewById(R.id.button);
		tv = (TextView)findViewById(R.id.tv);
		
	}
	
	public void mOnClick(View view) {
	      
		ConnectivityManager connMgr = (ConnectivityManager) 
		        getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		isMobileConn = networkInfo.isConnected();
		Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
		Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
		
		if(isWifiConn || isMobileConn) {
			tv.setText("Wifi available");
            new DownloadImageTask().execute("https://sbt.secondpay.net/image/");
            new DownloadJsonTask().execute("https://sbt.secondpay.net/json/");

		}
		
		if (isWifiConn == true) {
			// if WIFI connection available, download image, parse JSON, streaming music
			
			// download image
			
		}
		
	}
	
	public void sendJson(View view) {
		  JSONObject object = new JSONObject();
		  try {
		    object.put("name", "Jack Hack");
		    object.put("score", Integer.valueOf(200));
		    object.put("current", Double.valueOf(152.32));
		    object.put("nickname", "Hacker");
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		  TextView tv = (TextView)findViewById(R.id.jsonResult);
		  tv.setText(object.toString());
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
              
            try {
                return downloadImage(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }
        
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap bm) {
            //tv.setText(result);
        	ImageView imageView = (ImageView) findViewById(R.id.imageView);
        	imageView.setImageBitmap(bm);

       }
    }
	
	private class DownloadJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
              
            try {
                return downloadJson(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }
        
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String json) {
            //tv.setText(result);
        	TextView jsonText = (TextView) findViewById(R.id.textViewJson);
        	try {
        		JSONObject jObj = new JSONObject(json); 
        		JSONObject stuff = jObj.getJSONObject("stuff").getJSONObject("othertype");
        		String stuffStr = stuff.getString("company");
        		String aJsonString = jObj.getString("name");
            	jsonText.setText(stuffStr);

        	} catch (JSONException e) {
        		
        	}
       }
    }
	
	private Bitmap downloadImage(String myurl) throws IOException {
	    InputStream is = null;

	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();
        	Bitmap bitmap = BitmapFactory.decodeStream(is);

	        return bitmap;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	private String downloadJson(String myurl) throws IOException {
	    InputStream is = null;

	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        return readIt(is, 500);
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	// Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	public boolean isOnline() {
	    ConnectivityManager connMgr = (ConnectivityManager) 
	            getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}



