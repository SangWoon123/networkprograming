package server;

import java.net.Socket;

public class SharedArea {
    public static Socket login_socket;
    public static Socket login_client_socket=null;
    public static String tologin_msg=null; //클라이언트가 로그인 모듈로 보내는 메시지를 저장
    public static String replylogin_msg=null; //로그인 모듈이 요청을 보냈던 클라이언트에게 보내는 메시지를 저장
    public static boolean replylogin_exist=false;
    public static Socket bookmark_socket;
    public static Socket bookmark_client_socket;
    public static String tobookmark_msg="즐겨찾기 모듈로 전달할 메시지가 저장되지 않았습니다"; //클라이언트가 즐겨찾기 모듈로 보내는 메시지를 저장
    public static String replybookmark_msg="즐겨찾기 모듈의 응답메시지가 저장되지 않았습니다"; //즐겨찾기 모듈이 요청을 보냈던 클라이언트에게 보내는 메시지를 저장
    public static Socket findtour_socket;
    public static Socket findtour_client_socket;
    public static String tofindtour_msg;
    public static String replyfindtour_msg;


}
