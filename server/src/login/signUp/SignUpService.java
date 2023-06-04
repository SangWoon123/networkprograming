package login.signUp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SignUpService {
    private Socket m_Socket;
    private static final String USER_DATA_FILE = "login/users.txt";


    public boolean signUp(String username, String password, Socket d_socket) {

        m_Socket = d_socket;
        PrintWriter sendWriter = null;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (userExists(username,d_socket)) {
            System.out.println("이미 등록된 사용자 이름입니다.");
            sendWriter.println("replylogin>이미 등록된 사용자 이름입니다.");
            sendWriter.flush();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("파일에 데이터를 쓰는 중 오류가 발생했습니다.");
            sendWriter.println("replylogin>파일에 데이터를 쓰는 중 오류가 발생했습니다.");
            sendWriter.flush();
            return false;
        }

        System.out.println("회원가입이 완료되었습니다.");
        sendWriter.println("replylogin>회원가입이 완료되었습니다.");
        sendWriter.flush();
        return true;
    }

    private boolean userExists(String username,Socket d_socket) {
        m_Socket = d_socket;
        PrintWriter sendWriter;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scanner = new Scanner(new File(USER_DATA_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userData = line.split(",");
                String storedUsername = userData[0];
                if (username.equals(storedUsername)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.println("replylogin>파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.flush();
        }

        return false;
    }
}