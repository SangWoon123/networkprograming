package login.signIn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SignInService {

    private Socket m_Socket;
    private static final String USER_DATA_FILE = "server/src/login/users.txt";

    public boolean signIn(String username, String password, Socket d_socket) {

        m_Socket = d_socket;
        PrintWriter sendWriter = null;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String storedUsername = userData[0];
                String storedPassword = userData[1];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    System.out.println("로그인 성공!");
                    sendWriter.println("replylogin>로그인 성공!");
                    sendWriter.flush();
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.println("replylogin>파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.flush();
        }

        System.out.println("사용자 이름 또는 비밀번호가 일치하지 않습니다.");
        sendWriter.println("replylogin>사용자 이름 또는 비밀번호가 일치하지 않습니다.");
        sendWriter.flush();
        return false;
    }
}
