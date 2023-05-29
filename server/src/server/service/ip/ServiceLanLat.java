package server.service.ip;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;


import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ServiceLanLat implements ServiceInterface {
    @Override
    public JSONObject processRequest(Socket socket) {
        // IP2Location.io API 키
        String apiKey = "97F29EA686923D53EBEAD7167B844164";

        // IP 주소
        String ipAddress = "123.45.67.89";

        // API 요청 URL 생성
        String url = "https://api.ip2location.io/v2/" + ipAddress + "?key=" + apiKey + "&include=latlong";

        // HttpClient 생성
        HttpClient httpClient = HttpClient.newHttpClient();

        Map<String, Double> location=new HashMap<>();

        // API 호출
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response=httpClient.send(request,HttpResponse.BodyHandlers.ofString());
            String responseString=response.body();

//            // JSON 응답 파싱
            JSONObject json = new JSONObject(responseString);
//            double latitude = json.getDouble("latitude");
//            double longitude = json.getDouble("longitude");
//
//            location.put("latitude",latitude);
//            location.put("longitude",longitude);

            // 위도와 경도 출력
            return json;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
    }

