package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{

    private Socket m_Socket;

    @Override
    public void run() {
        // TODO Auto-generated method stub

        super.run();

        try {
            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream(),"UTF-8"));

            String receiveString;
            String[] split;

            while(true)
            {
                receiveString = tmpbuf.readLine();



                System.out.println(receiveString);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public void setSocket(Socket _socket)
    {
        m_Socket = _socket;
    }

}
