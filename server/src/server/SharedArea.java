package server;

import java.net.Socket;

public class SharedArea { //소켓간 데이터를 공유할 수 있는 클래스
    public static Socket login_socket;
    public static Socket login_client_socket;
    public static String tologin_msg=null; //클라이언트가 로그인 모듈로 보내는 메시지를 저장
    public static String replylogin_msg=null; //로그인 모듈이 요청을 보냈던 클라이언트에게 보내는 메시지를 저장
    public static Socket bookmark_socket;
    public static Socket bookmark_client_socket;
    public static String tobookmark_msg=null; //클라이언트가 즐겨찾기 모듈로 보내는 메시지를 저장
    public static String replybookmark_msg=null; //즐겨찾기 모듈이 요청을 보냈던 클라이언트에게 보내는 메시지를 저장
    public static Socket findtour_socket;
    public static Socket findtour_client_socket;
    public static String tofindtour_msg=null;
    public static String replyfindtour_msg=null;


}
