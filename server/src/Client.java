import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 9000;

        try {
            System.out.println("서버에 연결중입니다. " + serverName + " 포트 " + port);

            Socket client = new Socket(serverName, port);
            if(client!=null){
                System.out.println("서버에 연결되었습니다.");
            }

            InputStream inputStream = client.getInputStream(); //입력(요청) 스트림 받아오기
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //요청


            String response = reader.readLine();


            // JSON 형태의 문자열을 Map 객체로 파싱


            JSONObject jsonObject=new JSONObject(response);
            double latitude = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");

            System.out.println("위도: "+latitude+"경도: "+longitude);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
