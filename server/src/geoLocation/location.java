package geoLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class location {
	
	public static void main(String[] args) {
		
		String apiKey = "97F29EA686923D53EBEAD7167B844164";
		
		String url = "https://api.ip2location.io/v2/" + "118.222.85.227" + "?key=" + apiKey + "&include=latlong";
		
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
		    
		    System.out.println(temp[0].toString());
		    System.out.println(temp[1].toString());
	} catch(IOException e) {
		e.printStackTrace();
	}
	}
}
