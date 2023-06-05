package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class MainServer {

    public static ArrayList<PrintWriter> m_OutputList;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        m_OutputList = new ArrayList<PrintWriter>();
        try {
            ServerSocket s_socket = new ServerSocket(9370);

            while(true)
            {
                Socket c_socket = s_socket.accept();//연결 요청이 들어온 클라이언트와 연결될 소켓을 c_socket에 저장


                ServerToServerThread cs_thread = new ServerToServerThread(); //클라이언트가 보낸 메시지를 로그인,즐겨찾기,관광지 추천 중 해당하는 곳으로 보내는 쓰레드
                cs_thread.setSocket(c_socket);

                OtherToMainServerThread c_thread = new OtherToMainServerThread();
                c_thread.setSocket(c_socket);

                m_OutputList.add(new PrintWriter(c_socket.getOutputStream()));

                cs_thread.start();
                c_thread.start();
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
