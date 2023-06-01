package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import signUp.SignUpService;
import signIn.SignInService;

public class ReceiveThread extends Thread{

    private static final SignUpService signUpService = new SignUpService();
    private static final SignInService signInService = new SignInService();
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
                if(split.length >= 3 && split[0].equals("signUp"))
                {
                    signUpService.signUp(split[1], split[2],m_Socket);
                }
                if(split.length >= 3 && split[0].equals("signIn"))
                {
                    signInService.signIn(split[1], split[2],m_Socket);
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
