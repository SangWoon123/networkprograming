package geoLocation;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class geoLocation {
	
	public Double[] GeoLocation(String IpAddress) {
		
		String apiKey = "97F29EA686923D53EBEAD7167B844164";
		
		String url = "https://api.ip2location.io/v2/" + IpAddress + "?key=" + apiKey + "&include=latlong";

		// HttpClient 생성
		HttpClient httpClient = HttpClientBuilder.create().build();
		Double temp[] = null;
		
		// API 호출
		try {
		    HttpGet request = new HttpGet(url);
		    HttpResponse response = httpClient.execute(request);
		    HttpEntity entity = response.getEntity();
		    String responseString = EntityUtils.toString(entity);

		    // JSON 응답 파싱
		    JSONObject json = new JSONObject(responseString);
		    double latitude = json.getDouble("latitude");
		    double longitude = json.getDouble("longitude");

		    temp = new Double[2];
		    temp[0] = latitude;
		    temp[1] = longitude;
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return temp;
	}
}
