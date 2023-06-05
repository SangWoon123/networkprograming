package geoLocation;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class geoLocation {

	public Double[] GeoLocation(String IpAddress) {

		String apiKey = "97F29EA686923D53EBEAD7167B844164";

		String url = "https://api.ip2location.io/v2/" + IpAddress + "?key=" + apiKey + "&include=latlong";

		String result = "";

		Double[] temp = null;

		try {

			URL Url = new URL(url);

			HttpURLConnection conn = (HttpURLConnection)Url.openConnection();

			conn.setRequestProperty("Content-type",  "application/json");

			BufferedReader bf;
			bf = new BufferedReader(new InputStreamReader(Url.openStream(), "UTF-8"));
			result = bf.readLine();

			JSONObject json = new JSONObject(result);
			double latitude = json.getDouble("latitude");
			double longitude = json.getDouble("longitude");

			temp = new Double[2];
			temp[0] = latitude;
			temp[1] = longitude;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
}
