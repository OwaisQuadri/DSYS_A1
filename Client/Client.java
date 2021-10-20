
import java.io.*;
import java.net.Socket;
import java.rmi.*;

public class Client {

    final static int PORT_NUMBER = 1234;

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Usage: java Email username password");
            System.exit(0);
        }
        String username = argv[0];
        String password = argv[1];
        // open socket
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("nameOrIP", PORT_NUMBER);// name/ip address, port number
        } catch (Exception e) {
            e.printStackTrace();
        }
        // create input strm
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create output strm
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // clientSocket, input, output ready

        // check if UN/PW align

        // close sockets
        try {
            input.close();
            output.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}