package TourDataService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import Tour.TourAPI;
import geoLocation.geoLocation;
public class TourDataService {

    private Socket m_Socket;
    private static final TourAPI TOUR_API = new TourAPI();
    private static final geoLocation GEO_LOCATION = new geoLocation();

    public boolean TourDataService(String IpData, Socket d_socket) {
        m_Socket = d_socket;
        PrintWriter sendWriter = null;

        try {
           sendWriter = new PrintWriter(m_Socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String TourData = TOUR_API.TourInfor(GEO_LOCATION.GeoLocation(IpData));
        sendWriter.println("TourData>" + TourData);
        sendWriter.flush();

        return true;
    }
}
