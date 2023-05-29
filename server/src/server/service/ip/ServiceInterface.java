package server.service.ip;

import org.json.JSONObject;

import java.net.Socket;

public interface ServiceInterface {
    public JSONObject processRequest(Socket socket);
}
