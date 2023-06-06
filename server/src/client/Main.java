package client;

import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Main {

	public static String UserID = "findtour";//시작할 때 보낼 모듈 이름
	public static void main(String[] args) {
		
		try {
			Socket c_socket = new Socket("127.0.0.1", 9370);
			
			System.out.println("dkdkdk");
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}