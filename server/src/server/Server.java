package server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private ServerSocket serverSocket;
    private static ExecutorService threadPool= Executors.newFixedThreadPool(3);

    public Server(int port){
        try{
            serverSocket=new ServerSocket(port);
            System.out.println("서버를 시작합니다.");
            System.out.println("Waiting for a client...");
            while (true){
                Socket socket=serverSocket.accept();

                //스레드를 생성하여 클라이언트 요청을 처리
                threadPool.execute(new ClientHandler(socket));

//                //클라이언트 위치 서비스 모듈 호출
//                //IP 호출
//                // 관광 모듈 호출
//                //클라이언트 응답

                //socket.close();  소켓이 스레드로 동작하기때문에 에러발생함
            }
        }catch (IOException e){
            System.out.println("Server exception: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
