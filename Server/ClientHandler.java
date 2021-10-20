package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {// for multithreading client requests
    private final Socket client;

    // Constructor
    public ClientHandler(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {
        //init
        BufferedReader input = null;
        DataOutputStream output = null;
        PrintWriter writer = null;
        // create input/output/printwriter stream
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // output = new DataOutputStream(client.getOutputStream());
            writer = new PrintWriter(client.getOutputStream());
            // input, output ready
            writer.println("connected");
            writer.flush();
            System.out.println("message sent successfully");

            //read from input
            String line;
            while ((line = input.readLine()) != null) {
                //write message on Server
                System.out.printf("Sent from a client : %s\n", line);
                //send ack to client
                writer.println(line);
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                // if (output !=null){
                // output.close();
                // }
                if (input !=null){
                    input.close();
                }
                if (writer !=null){
                    writer.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        
        }
        
    }

}
