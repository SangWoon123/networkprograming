package server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class Server {
    private ServerSocket serverSocket;
    private static final int MAX_CLIENTS=3;
    private static int connectionClients=0;
    private static ExecutorService threadPool= Executors.newFixedThreadPool(MAX_CLIENTS);
    private static final Logger logger=Logger.getLogger(Server.class.getName());

    public Server(int port){
        try{
            serverSocket=new ServerSocket(port);
            logger.info("서버를 시작합니다.");
            logger.info("Waiting for a client...");
            while (true){
                Socket socket=serverSocket.accept();

                synchronized (Server.class){
                    if(connectionClients>=MAX_CLIENTS){
                        logger.warning("클라이언트 접속이 제한되었습니다. 최대 3명중 현재 클라이언트 수: "+connectionClients);
                        socket.close();
                        continue;
                    }
                    connectionClients++;
                }

                logger.info("클라이언트가 접속했습니다: "+socket.getInetAddress());

                //스레드를 생성하여 클라이언트 요청을 처리
                threadPool.execute(new ClientHandler(socket));

//                //클라이언트 위치 서비스 모듈 호출
//                //IP 호출
//                // 관광 모듈 호출
//                //클라이언트 응답
            }
        }catch (IOException e){
            System.out.println("Server exception: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
