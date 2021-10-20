
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;

public class Server {
    final static int PORT_NUMBER = 1234;

    public static void main(String argv[]) {
        // open socket
        ServerSocket service = null;
        Socket serverSocket = null;
        try {
            service = new ServerSocket(PORT_NUMBER);
            serverSocket = service.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create input stream
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create output stream
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // serverSocket, input, output ready

        // close socket(s)
        try {
            input.close();
            output.close();
            serverSocket.close();
            service.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}