package TourData_Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import TourDataService.TourDataService;

public class ReceiveThread extends Thread {
    private Socket m_Socket;
    private static final TourDataService TOUR_DATA_SERVICE = new TourDataService();

    public void run() {
        super.run();

        try{
            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream(), "UTF-8"));

            String receiveString;
            String[] split;

            while(true) {
                receiveString = tmpbuf.readLine();
                System.out.println("받은 메시지 : " + receiveString);
                split = receiveString.split(">");

                if(split[0].equals("tourList")) TOUR_DATA_SERVICE.TourDataService(split[1], m_Socket);
            }
        } catch (IOException e) {

        }
    }

    public void setSocket(Socket _socket) {
        m_Socket = _socket;
    }
}
