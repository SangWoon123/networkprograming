package server;

import com.google.gson.Gson;
import server.service.ServiceInterface;
import server.service.ServiceLanLat;

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

            //사용자 위치 반환
            Map<String,Double> message=serviceInterface.processRequest(socket);

            OutputStream outputStream=socket.getOutputStream(); // 응답을위한 소켓얻어오기
            PrintWriter printWriter=new PrintWriter(outputStream,true); // 응답 메시지 작성
            printWriter.println(message); //응답

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
