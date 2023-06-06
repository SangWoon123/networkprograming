package TourList.Tour_Connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class Main {
    public static String UserID = "login";

    public static void main(String[] args) {

        try{
            Socket c_socket = new Socket("127.0.0.1", 9370);

            ReceiveThread rec_thread = new ReceiveThread();
            rec_thread.setSocket(c_socket);

            SendThread send_thread = new SendThread();
            send_thread.setSocket(c_socket);

            send_thread.start();
            rec_thread.start();
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
