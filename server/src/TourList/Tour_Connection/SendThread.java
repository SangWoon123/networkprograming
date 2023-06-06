package TourList.Tour_Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class SendThread extends Thread {
    private Socket m_Socket;

    public void run() {
        super.run();

        try {
            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));

            PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());

            String sendString;

            sendWriter.println("ID:" + Main.UserID);
            sendWriter.flush();

            while(true) {
                sendString = tmpbuf.readLine();

                sendWriter.println(sendString);
                sendWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocket(Socket _socket) {
        m_Socket = _socket;
    }
}