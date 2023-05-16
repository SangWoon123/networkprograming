import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class GeoLocation {
    public static void main(String[] args) {
        // IP2Location.io API 키
        String apiKey = "API키 입력";

        // IP 주소
        String ipAddress = "123.45.67.89";

        // API 요청 URL 생성
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

            // 위도와 경도 출력
            System.out.println("위도: " + latitude);
            System.out.println("경도: " + longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}