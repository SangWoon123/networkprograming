package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerToServerThread extends Thread{

    private Socket m_socket;

    @Override
    public void run() {
        // TODO Auto-generated method stub

        super.run();
        try {

            PrintWriter sendWriter = new PrintWriter(m_socket.getOutputStream());

            while(true)
            {
                Thread.sleep(5);

                if(m_socket.equals(SharedArea.login_socket) && SharedArea.tologin_msg!=null){ //로그인 모듈과 연결된 소켓
                    sendWriter.println(SharedArea.tologin_msg);
                    sendWriter.flush();
                    SharedArea.tologin_msg=null;
                }
                if(m_socket.equals(SharedArea.login_client_socket) && SharedArea.replylogin_msg!=null){ //로그인 모듈에 메시지를 보낸 클라이언트와 연결된 소켓
                    Thread.sleep(20);
                    sendWriter.println(SharedArea.replylogin_msg);
                    sendWriter.flush();
                    SharedArea.replylogin_msg=null;
                    SharedArea.login_client_socket=null;
                }
                if(m_socket.equals(SharedArea.bookmark_socket) && SharedArea.tobookmark_msg!=null){ //즐겨찾기 모듈과 연결된 소켓
                    sendWriter.println(SharedArea.tobookmark_msg);
                    sendWriter.flush();
                    SharedArea.tobookmark_msg=null;
                }
                if(m_socket.equals(SharedArea.bookmark_client_socket) && SharedArea.replybookmark_msg!=null){ //즐겨찾기 모듈에 메시지를 보낸 클라이언트와 연결된 소켓
                    //System.out.println("즐겨찾기 모듈의 응답 메시지(mainserver->client):" + SharedArea.replybookmark_msg);
                    Thread.sleep(100);
                    sendWriter.println(SharedArea.replybookmark_msg);
                    sendWriter.flush();
                    SharedArea.replybookmark_msg=null;
                    SharedArea.bookmark_client_socket=null;
                }
                if(m_socket.equals(SharedArea.findtour_socket) && SharedArea.tofindtour_msg!=null){ //관광지 추천 모듈과 연결된 소켓
                    sendWriter.println(SharedArea.tofindtour_msg);
                    sendWriter.flush();
                    SharedArea.tofindtour_msg=null;
                }
                if(m_socket.equals(SharedArea.findtour_client_socket) && SharedArea.replyfindtour_msg!=null){ //관광지 모듈에 메시지를 보낸 클라이언트와 연결된 소켓
                    System.out.println("즐겨찾기 모듈의 응답 메시지(mainserver->client):" + SharedArea.replybookmark_msg);
                    Thread.sleep(100);
                    sendWriter.println(SharedArea.replyfindtour_msg);
                    sendWriter.flush();
                    SharedArea.replyfindtour_msg=null;
                    SharedArea.findtour_client_socket=null;
                }
            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSocket(Socket _socket)
    {
        m_socket = _socket;
    }
}
