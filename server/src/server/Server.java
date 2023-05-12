package server;

import server.service.ServiceInterface;
import server.service.ServiceLonLat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port){
        try{
            serverSocket=new ServerSocket(port);
            System.out.println("서버를 시작합니다.");
            System.out.println("Waiting for a client...");
            while (true){
                Socket socket=serverSocket.accept();

                //클라이언트 위치 서비스 모듈 호출
                ServiceInterface service=new ServiceLonLat();
                Map<String, Double> latLan = service.processRequest(socket);

                //클라이언트 응답
                OutputStream outputStream=socket.getOutputStream();
                PrintWriter writer=new PrintWriter(outputStream,true);
                writer.println(latLan);

                socket.close();
            }
        }catch (IOException e){
            System.out.println("Server exception: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
