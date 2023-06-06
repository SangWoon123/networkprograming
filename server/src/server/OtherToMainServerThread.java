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
            boolean loginStatus = false;
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

                if(!loginStatus && !m_ID.equals("login") && !m_ID.equals("bookmark") && !m_ID.equals("findtour")){ //비로그인상태에서 제시되는 선택지
                    sendWriter.println("1.로그인 2.회원가입 3.관광지 추천 받기 4.종료");
                    sendWriter.flush();
                    System.out.println("1.로그인 2.회원가입 3.관광지 추천 받기 4.종료");
                    text = tmpbuffer.readLine();
                    choice = text;
                    switch(choice){ //로그인 요청
                        case "1":
                            SharedArea.login_client_socket=m_socket; //id 입력받음
                            sendWriter.println("id:");
                            sendWriter.flush();
                            inputid = tmpbuffer.readLine();
                            m_ID=inputid;
                            sendWriter.println("pw:");//비밀번호 입력받음
                            sendWriter.flush();
                            inputpw = tmpbuffer.readLine();
                            SharedArea.tologin_msg="signIn>" + inputid + ">"+inputpw; // 로그인 모듈에서 이해할 수 있도록 메시지타입과 id,password정보를 묶은 메시지를 작성해 로그인 모듈과 연결된 소켓 쓰레드가 접근 가능한 장소에 저장
                            while(SharedArea.login_client_socket!=null){ //응답 메시지가 올 때 까지 기다림
                                Thread.sleep(1);//
                                if(SharedArea.replylogin_msg !=null){
                                    if(SharedArea.replylogin_msg.equals("로그인 성공!")){ //로그인이 성공한 경우
                                        m_ID=inputid;
                                        loginStatus=true;// 로그인 상태로 전환하여 저장함
                                    }
                                }
                            }
                            break;
                        case "2": //회원가입 요청
                            SharedArea.login_client_socket=m_socket;
                            sendWriter.println("id:");
                            sendWriter.flush();
                            inputid = tmpbuffer.readLine();
                            sendWriter.println("pw:");
                            sendWriter.flush();
                            inputpw = tmpbuffer.readLine();
                            SharedArea.tologin_msg="signUp>" + inputid + ">"+inputpw;
                            System.out.println("여기야2"+SharedArea.replylogin_msg);
                            while(SharedArea.login_client_socket!=null) Thread.sleep(1);
                            break;
                        case "3": //관광지 추천 요청
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
                            String inputip = tmpbuffer.readLine();
                            SharedArea.tofindtour_msg="tourList>" + inputip;
                            while(SharedArea.findtour_client_socket!=null) Thread.sleep(1);
                            break;
                        case "4":
                            System.out.println("종료");
                            choice="exit";
                            break;
                        default: //잘못 입력한 경우
                            sendWriter.println("1~4의 숫자를 입력해주세요");
                            sendWriter.flush();
                    }
                }
                else if(loginStatus && !m_ID.equals("login") && !m_ID.equals("bookmark") && !m_ID.equals("findtour")){ //로그인상태에서 제시되는 선택지
                    sendWriter.println("1.관광지 추천 받기 2.즐겨찾기 보기 3.즐겨찾기 추가 4.종료");
                    sendWriter.flush();
                    System.out.println("1.관광지 추천 받기 2.즐겨찾기 보기 3.즐겨찾기 추가 4.종료");
                    text = tmpbuffer.readLine();
                    choice = text;
                    switch(choice){
                        case "1": //관광지 추천 요청
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
                            while(SharedArea.findtour_client_socket!=null) Thread.sleep(1);
                            break;
                        case "2"://즐겨찾기 보기
                            SharedArea.bookmark_client_socket=m_socket;
                            SharedArea.tobookmark_msg="viewBookmark>" + m_ID;
                            while(SharedArea.bookmark_client_socket!=null)Thread.sleep(1);
                            break;
                        case "3": //즐겨찾기 추가
                            SharedArea.bookmark_client_socket=m_socket;
                            sendWriter.println("장소 정보를 입력하시오:");
                            sendWriter.flush();
                            String placedata = tmpbuffer.readLine();
                            SharedArea.tobookmark_msg="addBookmark>" + m_ID + ">" + placedata;
                            while(SharedArea.bookmark_client_socket!=null)Thread.sleep(1);
                            break;
                        case "4":
                            System.out.println("종료");
                            choice="exit";
                            break;
                        default://잘못 입력한 경우
                            sendWriter.println("1~3의 숫자를 입력해주세요");
                            sendWriter.flush();
                    }
                }
                if(choice.equals("exit")) break;

                if(m_ID.equals("login") || m_ID.equals("bookmark") || m_ID.equals("findtour")){ //기능 모듈과 메인서버를 연결하는 소켓 쓰레드
                    text = tmpbuffer.readLine();
                    String[] split = text.split(">");
                    msgtype = split[0];
                    switch (msgtype){ //각각의 기능 모듈에서 응답 메시지가 오면 메시지 타입을 빼고 전달할 데이터만 저장
                        case"replylogin":
                            SharedArea.replylogin_msg = split[1];
                            break;
                        case"TourData":
                            SharedArea.replyfindtour_msg = split[1];
                            break;
                        case"replybookmark":
                            SharedArea.replybookmark_msg = split[1];
                            break;
                    }
                }

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
