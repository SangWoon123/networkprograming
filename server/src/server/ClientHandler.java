package server;

import server.service.ip.ServiceInterface;
import server.service.ip.ServiceLanLat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ServiceInterface serviceInterface=new ServiceLanLat();
            //클라이언트 위치 서비스 모듈 호출
            // IP호출 사용자 위치 반환
            Map<String,Double> message=serviceInterface.processRequest(socket);

            // 관광 모듈 호출
            //클라이언트 응답




            OutputStream outputStream=socket.getOutputStream(); // 응답을위한 소켓얻어오기
            PrintWriter printWriter=new PrintWriter(outputStream,true); // 응답 메시지 작성
            printWriter.println(message); //응답

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
