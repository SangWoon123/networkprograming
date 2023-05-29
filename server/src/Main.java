import server.Server;

public class Main {
    public static void main(String[] args) {
        int port=Integer.parseInt("9000");
        new Server(port);
    }
}