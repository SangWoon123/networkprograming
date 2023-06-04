package bookmark.addBookmark;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AddService {
    private Socket m_Socket;
    private static final String BOOKMARK_DATA_FILE = "server/src/bookmark/bookmark.txt";


    public boolean add(String username, String placedata, Socket d_socket) {

        m_Socket = d_socket;
        PrintWriter sendWriter = null;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (placeExists(username, placedata, d_socket)) {
            System.out.println("이미 등록된 장소입니다.");
            sendWriter.println("replybookmark>이미 등록된 장소입니다.");
            sendWriter.flush();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKMARK_DATA_FILE, true))) {
            writer.write(username + "," + placedata);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("파일에 데이터를 쓰는 중 오류가 발생했습니다.");
            sendWriter.println("replybookmark>파일에 데이터를 쓰는 중 오류가 발생했습니다.");
            sendWriter.flush();
            return false;
        }

        System.out.println("즐겨찾기에 추가되었습니다");
        sendWriter.println("replybookmark>즐겨찾기에 추가되었습니다");
        sendWriter.flush();
        return true;
    }

    private boolean placeExists(String username,String placedata, Socket d_socket) {
        m_Socket = d_socket;
        PrintWriter sendWriter = null;
        try {
            sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scanner = new Scanner(new File(BOOKMARK_DATA_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] placeData = line.split(",");
                String storedUsername = placeData[0];
                String storedPlacedata = placeData[1];
                if (username.equals(storedUsername) && placedata.equals(storedPlacedata)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.println("replybookmark>파일에서 데이터를 읽는 중 오류가 발생했습니다.");
            sendWriter.flush();
        }

        return false;
    }
}
