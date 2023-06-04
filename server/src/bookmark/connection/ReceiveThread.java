package bookmark.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import bookmark.addBookmark.AddService;
import bookmark.viewBookmark.ViewService;

public class ReceiveThread extends Thread{

    private static final AddService addBookmark = new AddService();
    private static final ViewService viewBookmark = new ViewService();
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

                split = receiveString.split(">");
                if(split[0].equals("addBookmark"))
                {
                    addBookmark.add(split[1], split[2],m_Socket);
                }
                if(split[0].equals("viewBookmark"))
                {
                    viewBookmark.view(split[1],m_Socket);
                }

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
