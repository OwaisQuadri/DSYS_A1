package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Student {

    final static int PORT_NUMBER = 1234;
    final static String[] USERS = { "owais" };
    final static String[] PASSWORDS = { "owais" };

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Usage: java Client.Student username password");
            System.exit(0);
        }
        String username = argv[0];
        String password = argv[1];
        boolean login = false;
        for (int i = 0; i < USERS.length; i++) {
            if (username.equalsIgnoreCase(USERS[i])) {
                if (password.equals(PASSWORDS[i])) {
                    login = true;
                }
            }
        }
        if (!login) {
            System.out.println("Incorrect username or password");
            System.out.println("Usage: java Client.Student username password");
            System.exit(0);
        }

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
            System.out.println("Server status: " + input.readLine());
            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Supervisor replied " + input.readLine());
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
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}