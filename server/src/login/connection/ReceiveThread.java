package login.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import login.signUp.SignUpService;
import login.signIn.SignInService;

public class ReceiveThread extends Thread{

    private static final SignUpService signUpService = new SignUpService();
    private static final SignInService signInService = new SignInService();
    private Socket m_Socket;

    @Override
    public void run() {
        // TODO Auto-generated method stub

        super.run();

        String msgtype;
        try {
            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream(),"UTF-8"));

            String receiveString;
            String[] split;

            while(true)
            {
                receiveString = tmpbuf.readLine();
                System.out.println("받은메시지:" + receiveString);
                split = receiveString.split(">");
                msgtype = split[0];
                if(msgtype.equals("signUp")) signUpService.signUp(split[1], split[2],m_Socket);
                if(msgtype.equals("signIn")) signInService.signIn(split[1], split[2],m_Socket);

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
