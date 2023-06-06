package user;

import java.io.*;
import java.net.Socket;

public class Receive extends Thread{
    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();

        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receiveString;
            String[] split;
            while(true)
            {
                receiveString = reader.readLine();

                String[] placedata = receiveString.split("!");//관광지 추천받기 관련 메시지가 왔을 때 보기 좋게 출력
                String[] bookmark_placedata = receiveString.split(",");//즐겨찾기 보기 관련 메시지가 왔을 때 보기 좋게 출력

                if(placedata[0].equals("tour")) for(int i=1; i<placedata.length; i++) System.out.println(i+". "+placedata[i]);//등록 장소 수 만큼 줄바꿈하여 출력
                else if(bookmark_placedata[0].equals("viewb")) for(int i=1; i<bookmark_placedata.length; i++) System.out.println(i+". "+bookmark_placedata[i]);//등록 장소 수 만큼 줄바꿈하여 출력
                else System.out.println(receiveString);

                if(receiveString.equals("프로그램이 종료되었습니다")) break;
            }

            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }
}
