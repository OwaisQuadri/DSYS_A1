package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    final static int PORT_NUMBER = 1234;

    public static void main(String argv[]) {
        // if (argv.length != 2) {
        // System.out.println("Usage: java Email username password");
        // System.exit(0);
        // }
        // String username = argv[0];
        // String password = argv[1];

        // open socket
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", PORT_NUMBER);// name/ip address, port number
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
        // DataOutputStream output = null;
        // try {
        // output = new DataOutputStream(clientSocket.getOutputStream());
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // setup printwriter
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // object of scanner class
        Scanner sc = new Scanner(System.in);
        String line = null;
        // clientSocket, input, output ready
        try {
            System.out.println("Server status: "+input.readLine());
            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied " + input.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check if UN/PW align
        // close scanner
        sc.close();
        // close sockets
        try {
            input.close();
            // output.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}