

import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;


public class Client {
    private static final Logger logger=Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 9000;

        try {
            System.out.println("서버에 연결중입니다. " + serverName + " 포트 " + port);

            Socket client = new Socket(serverName, port);
            if(client!=null){
                logger.info("서버에 연결되었습니다.");
            }

            String option=getOptionsFromServer(client);
            System.out.println("서버로 부터 도착한 선택지: "+option);

            // 선택지에 따른 동작 수행
            if (option.contains("로그인")){
                // 사용자 입력받기
                BufferedReader userInputReader=new BufferedReader(new InputStreamReader(System.in));
                System.out.print("ID: ");
                String id=userInputReader.readLine();
                System.out.printf("Password: ");
                String password=userInputReader.readLine();

                // 서버로 데이터를 전송하기 위한 출력 스트림
                OutputStream outputStream=client.getOutputStream();
                PrintWriter writer=new PrintWriter(outputStream,true);

                //로그인 요청 데이터
                String userRequest="signIn>"+id+">"+password;
                // 서버로 보낼 데이터
                writer.println(userRequest);
            }else if (option.contains("회원가입")){
                System.out.println("회원가입");
                // 사용자 입력받기
                BufferedReader userInputReader=new BufferedReader(new InputStreamReader(System.in));
                System.out.printf("ID: ");
                String id=userInputReader.readLine();
                System.out.printf("Password: ");
                String password=userInputReader.readLine();

            } else if (option.contains("관광지")) {
                String myIp=getIPAddress();
                System.out.println("내 컴퓨터 IP: "+myIp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getOptionsFromServer(Socket client){

        try {
            // 서버로 선택지 요청
            OutputStream outputStream = client.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println("getOptions");

            // 서버 응답 받기
            InputStream inputStream = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String options = reader.readLine();

            // 스트림 닫기
            writer.close();
            reader.close();
            return options;

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getIPAddress(){
        try {
            InetAddress inetAddress=InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }

}
