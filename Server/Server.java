package Server;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int PORT_NUMBER = 1234;

    public static void main(String argv[]) {
        // open socket
        ServerSocket service = null;
        Socket serverSocket = null;
        try {
            //listen on port
            service = new ServerSocket(PORT_NUMBER);
            service.setReuseAddress(true);
            //accept requests
            while (true) {
                serverSocket = service.accept();
                System.out.println("new client connected: " + serverSocket.getInetAddress().getHostAddress());
                //new thread
                ClientHandler newThread = new ClientHandler(serverSocket);
                new Thread(newThread).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                service.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}