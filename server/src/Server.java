import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        int port = 9000;

        try {
            System.out.println("서버가 시작되었습니다. 포트: " + port);

            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("클라이언트가 접속하였습니다.");

                // 클라이언트 요청 처리를 위한 쓰레드 생성
                Thread thread = new ClientHandler(clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);

                // 클라이언트 요청 수신
                String request = reader.readLine();
                logger.info("클라이언트 요청: " + request);

                // 요청 처리
                String response = processRequest(request);
                logger.info("서버 응답: " + response);

                // 응답 전송
                writer.println(response);

                // 소켓 및 스트림 닫기
                writer.close();
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processRequest(String request) {
            // 요청 파싱
            String[] parts = request.split(">");
            String action = parts[0];

            // 요청에 따라 처리
            if (action.equals("getOptions")) {
                // 선택지 요청에 대한 처리
                return "로그인,회원가입,관광지";
            } else if (action.equals("signIn")) {
                // 로그인 요청에 대한 처리
                String id = parts[1];
                String password = parts[2];
                // 로그인 처리
                // ...
                return "로그인 성공"; // 로그인 성공 시 응답
            } else if (action.equals("signup")) {
                // 회원가입 요청에 대한 처리
                String id = parts[1];
                String password = parts[2];
                // 회원가입 처리
                // ...
                return "회원가입 성공"; // 회원가입 성공 시 응답
            } else if (action.equals("tourList")) {
                // 관광지 요청에 대한 처리
                String ip = parts[1];
                // 관광지 요청 처리
                // ...
                return "관광지 목록"; // 관광지 목록 응답
            }

            return "알 수 없는 요청";
        }
    }
}
