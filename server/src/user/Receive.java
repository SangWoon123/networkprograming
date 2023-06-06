package user;

import java.io.*;
import java.net.Socket;

public class Receive extends Thread{
    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();

        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receiveString;
            String[] split;
            while(true)
            {
                receiveString = reader.readLine();

                System.out.println(receiveString);
                if(receiveString.equals("프로그램이 종료되었습니다")) break;
            }

            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }
}
