package TourList.geoLocation;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class geoLocation {

    public Double[] GeoLocation(String IpAddress) {
        disableCertificateValidation();

        String apiKey = "97F29EA686923D53EBEAD7167B844164";

        String url = "https://api.ip2location.io/?key=" + apiKey + "&ip=" + IpAddress;

        Double[] temp = null;

        try {

            URL Url = new URL(url);

            HttpURLConnection connection = (HttpURLConnection)Url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                line = reader.readLine();

                System.out.println(line);

                JSONObject json = new JSONObject(line);
                double latitude = json.getDouble("latitude");
                double longitude = json.getDouble("longitude");
                reader.close();

                temp = new Double[2];
                temp[0] = latitude;
                temp[1] = longitude;
            } else {
                System.out.println("API 요청 실패. 응답 코드 : " + responseCode);
            }

            connection.disconnect();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return temp;
    }

    private static void disableCertificateValidation() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}