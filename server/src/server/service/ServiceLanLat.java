package server.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServiceLanLat implements ServiceInterface{
    @Override
    public Map<String, Double> processRequest(Socket socket) {
        Map<String,Double> lat=new HashMap<>();
        lat.put("lat",36.8888);
        lat.put("lan",127.23);
        return lat;
    }
}
