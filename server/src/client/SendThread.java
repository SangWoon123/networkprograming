package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Tour.TourAPI;
import geoLocation.geoLocation;

public class SendThread extends Thread{
	private Socket m_Socket;
	private static final geoLocation Geo_Location = new geoLocation();
	private static final TourAPI TourAPI = new TourAPI();
	
	public void run() {
		super.run();
		
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			
			String sendString;
			
			while(true) {
				
				sendString = tmpbuf.readLine();
				
				sendWriter.println("TourData>" + ReceiveThread.TourData);
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
