package user;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;


public class Client {
    private static final String serverName = "localhost";
    private static final int port = 9370;
    private static final Logger logger=Logger.getLogger(Client.class.getName());
    private static final Socket client;

    static {
        try {
            client = new Socket(serverName,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {


        logger.info("서버에 연결중입니다. " + serverName + " 포트 " + port);

        if (client != null) {
            logger.info("서버에 연결되었습니다.");
        }

        Receive receive=new Receive();
        receive.setSocket(client);

        Send send=new Send();
        send.setSocket(client);

        receive.start();
        send.start();

    }

}
