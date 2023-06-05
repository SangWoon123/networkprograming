package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import geoLocation.geoLocation;
import Tour.TourAPI;

public class ReceiveThread extends Thread {
	
	private static final geoLocation Geo_Location = new geoLocation();
	private static final TourAPI TourAPI = new TourAPI();
	public static String TourData = "";
	
	private Socket m_Socket;
	
	public void run() {
		
		super.run();
		
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream(), "UTF-8"));
			
			String receiveString;
			String[] split;
			
			while(true) {
				receiveString = tmpbuf.readLine();
				System.out.println("받은 메시지:" + receiveString); //테스트용으로 받은 메시지를 출력
				split = receiveString.split(">");
				if(split.length >= 2 && split[0].equals("tourList")) {
					TourData = TourAPI.TourInfor(Geo_Location.GeoLocation(split[1]));
				}
			}
		} catch(IOException e) {	
		}
	}
	
	public void setSocket(Socket _socket) {
		m_Socket = _socket;
	}
}
