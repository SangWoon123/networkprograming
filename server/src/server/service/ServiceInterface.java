package server.service;

import java.net.Socket;
import java.util.Map;

public interface ServiceInterface {
    public Map<String,Double> processRequest(Socket socket);
}
