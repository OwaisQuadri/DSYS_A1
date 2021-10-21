package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Student {

    final static int PORT_NUMBER = 1234;
    final static String[] USERS = { "owais" , "student"};
    final static String[] PASSWORDS = { "owais", "student"};

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
        // verify login
        if (!login) {
            System.out.println("Incorrect username or password");
            System.out.println("Usage: java Client.Student username password");
            System.exit(0);
        }
        // open socket
        Socket clientSocket = null;
        // create in/out strm
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // open socket
            clientSocket = new Socket("localhost", PORT_NUMBER);// name/ip address, port number
            // init input
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // init output
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Test Unavailable : try again later.");
            System.exit(0);
        }
        // clientSocket, input, output ready
        // create scanner
        Scanner sc = new Scanner(System.in);
        String line = null;

        try {
            System.out.println("Server status: " + in.readLine());
            //tell server who this is
            out.println(username);
            out.flush();
            String testName = in.readLine();
            System.out.println("\n" + testName + "\n");
            while (true) {
                line = in.readLine();
                if ("iosuhefiuherfiushzfgiu".equals(line)) {
                    String ans = sc.nextLine();
                    out.println(ans);
                } else if("exit".equals(line)){
                    System.exit(0);
                }else{
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // check if UN/PW align
        // close scanner
        sc.close();
        // close sockets

    }
}