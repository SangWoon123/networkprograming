package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class OtherToMainServerThread extends Thread{


    private Socket m_socket;
    private String m_ID = "client";
    String inputid;
    String inputpw;
    String choice = "";
    @Override
    public void run() {
        // TODO Auto-generated method stub


        super.run();
        try {
            BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream())); //소켓을 통해 들어운 메시지를 tmpbuffer로 저장
            String text;
            String msgtype;
            PrintWriter sendWriter = new PrintWriter(m_socket.getOutputStream());
            boolean loginStaus = false;
            text = tmpbuffer.readLine();//첫번째 입력(모듈이름을 알려줌)을 받고 다음으로 넘어감
            while(true)
            {
                System.out.println(m_ID+ "가 보낸 메시지:" +text); //받은 메시지 출력(메인서버 정상 동작 확인용)

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
                    if(m_ID.equals("findtour")){
                        SharedArea.findtour_socket=m_socket;
                    }
                    System.out.println(m_ID + "이(가) 연결되었습니다.");
                }


                if(!loginStaus && !m_ID.equals("login") && !m_ID.equals("bookmark") && !m_ID.equals("findtour")){
                    sendWriter.println("1.로그인 2.회원가입 3.관광지 추천 받기 4.종료");
                    sendWriter.flush();
                    System.out.println("1.로그인 2.회원가입 3.관광지 추천 받기 4.종료");
                    text = tmpbuffer.readLine();
                    choice = text;
                    switch(choice){
                        case "1":
                            SharedArea.login_client_socket=m_socket;
                            sendWriter.println("id:");
                            sendWriter.flush();
                            inputid = tmpbuffer.readLine();
                            sendWriter.println("pw:");
                            sendWriter.flush();
                            inputpw = tmpbuffer.readLine();
                            SharedArea.tologin_msg="signIn>" + inputid + ">"+inputpw;
                            while(SharedArea.login_client_socket!=null)Thread.sleep(1);
                            break;
                        case "2":
                            SharedArea.login_client_socket=m_socket;
                            sendWriter.println("id:");
                            sendWriter.flush();
                            inputid = tmpbuffer.readLine();
                            sendWriter.println("pw:");
                            sendWriter.flush();
                            inputpw = tmpbuffer.readLine();
                            SharedArea.tologin_msg="signUp>" + inputid + ">"+inputpw;
                            while(SharedArea.login_client_socket!=null)Thread.sleep(1);
                            break;
                        case "3":
                            System.out.println("관광지 추천 받기");
                            SharedArea.findtour_client_socket=m_socket;
                            sendWriter.println("위치 정보를 얻는데 동의하십니까? (네/아니오):");
                            sendWriter.flush();
                            String allow = tmpbuffer.readLine();
                            while(!allow.equals("네") && !allow.equals("아니오")){ // (네/아니오)로 대답하지 않은 경우
                                sendWriter.println("(네/아니오)로 대답해주세요:");
                                sendWriter.flush();
                                System.out.println("(네/아니오)로 대답해주세요:");
                                allow = tmpbuffer.readLine();
                            }
                            if(allow.equals("아니오")) break;
                            String ipip = m_socket.getInetAddress().getHostAddress();//로컬호스트로 연결되어 로컬호스트를 반환함
                            System.out.println("ip : " + ipip);
                            String inputip = tmpbuffer.readLine();
                            SharedArea.tofindtour_msg="tourList>" + inputip + ">"+inputpw;
                            break;
                        case "4":
                            System.out.println("종료");
                            choice="exit";
                            break;
                    }
                }
                if(choice.equals("exit")) break;

                if(m_ID.equals("login") || m_ID.equals("bookmark") || m_ID.equals("findtour")){
                    text = tmpbuffer.readLine();
                    String[] split = text.split(">");
                    msgtype = split[0];
                    switch (msgtype){
                        case"replylogin":
                            SharedArea.replylogin_msg = split[1];
                            break;
                        case"TourData":
                            SharedArea.replyfindtour_msg = split[1];
                    }

                }
                /*else if(loginStaus && !m_ID.equals("login") && !m_ID.equals("bookmark") && !m_ID.equals("findtour")){
                    System.out.println("1.로그아웃 2.관광지 추천 받기 3.즐겨찾기 추가 4.즐겨찾기 보기 5.종료");
                    String choice = "ㅁ";//scanner.next();
                    switch(choice){
                        case "1":
                            System.out.println("로그아웃");
                        case "2":
                            System.out.println("관광지 추천 받기");
                        case "3":
                            System.out.println("즐겨찾기 추가");
                            break;
                        case "4":
                            System.out.println("즐겨찾기 보기");
                            break;
                        case "5":
                            System.out.println("종료2");
                            break;
                    }
                    if(choice.equals("5")) break;
                }
                if(choice.equals("exit")) break;
*/

                /*String[] split = text.split(">");
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
                    case"tourList":
                        SharedArea.findtour_client_socket=m_socket;
                        SharedArea.tofindtour_msg=text;
                        break;
                    case"TourData":
                        SharedArea.replyfindtour_msg = split[1];    
                }
                 */


            }

            System.out.println("프로그램 종료");
            sendWriter.println("프로그램이 종료되었습니다");
            sendWriter.flush();
            MainServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
            m_socket.close();

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
