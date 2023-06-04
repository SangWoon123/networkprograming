package bookmark.viewBookmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ViewService {

    private Socket m_Socket;
    private static final String USER_DATA_FILE = "server/src/bookmark/bookmark.txt";
    String storedUsername;
    String storedPlaceData;

    public boolean view(String username, Socket d_socket) {

        m_Socket = d_socket;
        PrintWriter sendWriter = null;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;

            String sendmsg = "";
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                storedUsername = userData[0];
                storedPlaceData = userData[1];

                if (username.equals(storedUsername)) {
                    sendmsg = sendmsg + ","+storedPlaceData;
                }
            }




            if(!sendmsg.equals("")) {
                sendWriter.println("replybookmark>" + sendmsg);
                sendWriter.flush();
                return true;
            }

        } catch (IOException e) {
            System.out.println("파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.println("replybookmark>파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.flush();
        }

        System.out.println("등록된 장소가 없습니당");
        System.out.println("보낸건" + "replybookmark>등록된 장소가 없습니다");
        sendWriter.println("replybookmark>등록된 장소가 없습니다");
        sendWriter.flush();
        return false;
    }
}
