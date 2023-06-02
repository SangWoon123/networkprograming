package GeoLocation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class GeoLocation {
	
	String apiKey = "97F29EA686923D53EBEAD7167B844164";
	String ipAddress = "";
	
	GeoLocation() {  };

	public Double[] GetLocation(String ip) {
		String url = "https://api.ip2location.io/v2/" + ipAddress + "?key=" + apiKey + "&include=latlong";

		// HttpClient 생성
		HttpClient httpClient = HttpClientBuilder.create().build();
		
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

		    Double temp[] = new Double[2];
		    temp[0] = latitude;
		    temp[1] = longitude;
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}