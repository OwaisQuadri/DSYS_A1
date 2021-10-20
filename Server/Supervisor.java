package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class Supervisor {
    final static int PORT_NUMBER = 1234;
    final static String[] USERS = { "admin" };
    final static String[] PASSWORDS = { "admin" };

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Usage: java Server.Supervisor username password");
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
            System.out.println("Usage: java Server.Supervisor username password");
            System.exit(0);
        }
        // open socket
        ServerSocket supervisor = null;
        Socket newStudent = null;
        try {
            // listen on port
            supervisor = new ServerSocket(PORT_NUMBER);
            supervisor.setReuseAddress(true);
            // accept requests
            while (true) {
                newStudent = supervisor.accept();
                System.out.println("new student connected: " + newStudent.getInetAddress().getHostAddress());
                // new thread
                ClientHandler newThread = new ClientHandler(newStudent);
                new Thread(newThread).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                supervisor.close();
                newStudent.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}