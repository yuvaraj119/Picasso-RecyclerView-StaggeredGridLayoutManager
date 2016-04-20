package com.recyclerviewstaggeredgridview;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.json.*;

import android.net.http.*;
import android.os.*;
import android.text.*;

import java.io.*;

/**
* Created by yuvaraj on 3/4/16.
*/
class LoadFlickerFeed extends AsyncTask<String, Void, String[]> {

	private static final String JSON_FLICKR_FEED_START = "jsonFlickrFeed({";
	private static final CharSequence JSON_FLICKR_FEED_END = "})";
	@Override
	protected String[] doInBackground(String... params) {
		String tags = "";
		for(String param : params) {
			if (tags.length() == 0) {
				tags = param;
			} else {
				tags = "," + param;
			}
		}
		return readUrl(String.format("https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=%s", tags));
	}

	private String[] readUrl(String urlString) {
		InputStream in = null;
		BufferedReader reader=null;
		try {
			AndroidHttpClient client = AndroidHttpClient.newInstance(System.getProperty("http.agent"));
			HttpUriRequest request = new HttpGet(urlString);
			HttpResponse response = client.execute(request);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String json = readJSON(reader);
			client.close();
			return parseJSON(json);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			closeStreams(in, reader);
		}
		return new String[0];
	}

	private void closeStreams(InputStream in, BufferedReader reader) {
		try {
			if(in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private String[] parseJSON(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray items = obj.getJSONArray("items");
			return parseItems(items);
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

	private String[] parseItems(JSONArray items) throws JSONException {
		String[] urls = new String[items.length()];
		for(int i = 0; i < items.length(); i++) {
			JSONObject obj = items.getJSONObject(i);
			obj = obj.getJSONObject("media");
			String urlString = obj.getString("m");
			if (!TextUtils.isEmpty(urlString)) {
				urls[i] = urlString;
			}
		}
		return urls;
	}

	private String readJSON(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while(line != null)
		{
			if (line.contains(JSON_FLICKR_FEED_START)) {
				line = "{";
			} else if (line.contains(JSON_FLICKR_FEED_END)) {
				line = "}";
			}
			sb.append(line);
			line = reader.readLine();
		}
		return sb.toString();
	}
}
