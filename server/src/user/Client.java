package user;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;


public class Client {
    private static final String serverName = "localhost";
    private static final int port = 9370;
    private static final Logger logger=Logger.getLogger(Client.class.getName());
    private static final Scanner sc=new Scanner(System.in);
    private static final Socket client;
    public static String UserID;

    static {
        try {
            client = new Socket(serverName,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {


        logger.info("서버에 연결중입니다. " + serverName + " 포트 " + port);

        if (client != null) {
            logger.info("서버에 연결되었습니다.");
            System.out.print("서버로 보낼 메시지를 입력하세요: ");
        }

        Receive receive=new Receive();
        receive.setSocket(client);

        Send send=new Send();
        send.setSocket(client);

        receive.start();
        send.start();

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

    // 선택지 서버로 전송
    private static void sendChoiceToServer(Socket socket,String choice)throws IOException{
        // 서버로 선택지 전송
        OutputStream outputStream=socket.getOutputStream();
        PrintWriter printWriter=new PrintWriter(outputStream,true);
        printWriter.println(choice);

        printWriter.close();
    }

    private static void receiveLoginForm(Socket socket) throws IOException{
        // 서버로부터 로그인 폼 받기
        InputStream inputStream=socket.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String loginForm=bufferedReader.readLine();

        System.out.println("서버로부터 도착한 로그인 폼: "+loginForm);


        // 로그인 폼에 맞게  입력값 전송
        BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ID: ");
        String id=userInput.readLine();
        System.out.print("Password: ");
        String password=userInput.readLine();

        //서버로 데이터를 전송하기위한 출력스트림
        OutputStream outputStream=socket.getOutputStream();
        PrintWriter printWriter=new PrintWriter(outputStream,true);

        String userRequest="signIn>"+id+">"+password;

        printWriter.println(userRequest);

    }

    // 서버로부터 회원가입 폼 받기
    public static void receiveSignUpForm(Socket socket)throws IOException{
        // 서버로부터 회원가입 폼 받기
        InputStream inputStream=socket.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String signUpForm=bufferedReader.readLine();

        System.out.println("서버로 부터 도착한 회원 가입 폼: "+signUpForm);

        // 회원가입 폼에 맞게 데이터 전송
        BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ID: ");
        String id=userInput.readLine();
        System.out.print("Password: ");
        String password=userInput.readLine();

        // 서버로 데이터를 전송하기위한 스트림
        OutputStream outputStream=socket.getOutputStream();
        PrintWriter printWriter=new PrintWriter(outputStream,true);

        String userRequest="signup>"+id+">"+password;

        printWriter.println(userRequest);

    }

    private static void receiveTourList(Socket socket)throws IOException{
        // 서버로부터 폼 입력받기
        InputStream inputStream=socket.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String tourList=bufferedReader.readLine();

        System.out.println("서버로 부터 받은 폼: "+tourList);

        // 투어리스트 입력 폼에 맞게 작성
        System.out.print("IP: ");
        String ip=getIPAddress();

        //서버로 데이터를 전송하기 위한 출력 스트림
        OutputStream os=socket.getOutputStream();
        PrintWriter writer=new PrintWriter(os,true);

        // 관광지 요청 데이터
        String userRequest="tourList>"+ip;

        writer.println(userRequest);


    }

}
