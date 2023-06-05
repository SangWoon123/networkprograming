import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 9000;
        ServerSocket serverSocket = null;

        try {
            System.out.println("서버를 시작합니다. 포트: " + port);

            serverSocket = new ServerSocket(port);
            System.out.println("클라이언트의 연결을 기다립니다.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");


                // 클라이언트 요청 처리
                handleClientRequest(clientSocket);

                System.out.println("클라이언트와의 연결이 종료되었습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {
        if (clientSocket == null) {
            System.out.println("클라이언트 소켓이 null입니다.");
            return;
        }

        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); // 입력값 받기

        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true); // 출력+flush

        // 클라이언트로 선택지 전송
        writer.println("1. 로그인 2. 회원가입 3. 관광지");
        writer.flush();

        // 클라이언트의 선택 수신
        String clientChoice = reader.readLine();

        System.out.println("클라이언트로부터 온 메시지: " + clientChoice);

        // 선택에 따라 처리
        if (clientChoice != null) {
            if (clientChoice.equals("1")) {
                // 로그인 폼 전송
                writer.println("로그인 폼을 입력하세요.");
            } else if (clientChoice.equals("2")) {
                // 회원가입 폼 전송
                writer.println("회원가입 폼을 입력하세요.");
            } else if (clientChoice.equals("3")) {
                // 관광지 목록 전송
                writer.println("관광지 목록을 입력하세요.");
            } else {
                // 잘못된 선택 처리
                writer.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
        writer.flush();
    }

}
