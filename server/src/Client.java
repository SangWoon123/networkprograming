import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Client {

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 9000;

        try {
            System.out.println("서버에 연결중입니다. " + serverName + " 포트 " + port);

            Socket client = new Socket(serverName, port);
            System.out.println("서버에 연결되었습니다.");

            InputStream inputStream = client.getInputStream(); //입력(요청) 스트림 받아오기
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //요청


            String response = reader.readLine();
            System.out.println(response);

            Gson gson=new Gson();

            // JSON 형태의 문자열을 Map 객체로 파싱
            Type type = new TypeToken<Map<String, Double>>(){}.getType();
            Map<String, Double> latLan = gson.fromJson(response, type);

            System.out.println("위도"+latLan.get("lat")+"경도: "+latLan.get("lan"));




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
