package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class OtherToMainServerThread extends Thread{


    private Socket m_socket;
    private String m_ID = "client";


    @Override
    public void run() {
        // TODO Auto-generated method stub


        super.run();
        try {
            BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream())); //소켓을 통해 들어운 메시지를 tmpbuffer로 저장

            String text;
            String msgtype;

            while(true)
            {
                text = tmpbuffer.readLine();
                System.out.println(m_ID+ "가 보낸 메시지:" +text);
                if(text == null)
                {
                    System.out.println(m_ID + "이(가) 나갔습니다.");
                    for(int i = 0; i < MainServer.m_OutputList.size(); ++i)
                    {
                        MainServer.m_OutputList.get(i).println(m_ID + "이(가) 나갔습니다.");
                        MainServer.m_OutputList.get(i).flush();
                    }
                    break;
                }

                String[] split = text.split(">");
                msgtype = split[0];

                switch (msgtype){
                    case"signIn":
                    case"signUp":
                        SharedArea.login_client_socket=m_socket;
                        SharedArea.tologin_msg=text;
                        break;
                    case"replylogin":
                        SharedArea.replylogin_msg = split[1];
                        break;
                    case"addBookmark":
                    case"viewBookmark":
                        SharedArea.bookmark_client_socket=m_socket;
                        SharedArea.tobookmark_msg=text;
                        //System.out.println("클라이언트가 즐겨찾기 모듈로 보낸 메시지(client->mainserver):" + SharedArea.tobookmark_msg);
                        break;
                    case"replybookmark":
                        SharedArea.replybookmark_msg = split[1];
                        SharedArea.replylogin_exist=true;
                        break;
                    case"IpData":
                        SharedArea.findtour_client_socket=m_socket;
                        SharedArea.tofindtour_msg=text;
                        break;
                    case"TourData":
                        SharedArea.replyfindtour_msg = split[1];    
                }

                String[] idregister = text.split(":"); //연결 후 id를 전달받음
                if(idregister[0].equals("ID"))
                {
                    m_ID = idregister[1];
                    if(m_ID.equals("login")){
                        SharedArea.login_socket=m_socket;
                    }
                    if(m_ID.equals("bookmark")){
                        SharedArea.bookmark_socket=m_socket;
                    }
                    System.out.println(m_ID + "이(가) 연결되었습니다.");
                }

            }

            MainServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
            m_socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSocket(Socket _socket)
    {
        m_socket = _socket;
    }
}
