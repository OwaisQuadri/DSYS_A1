package Server;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClientHandler implements Runnable {// for multithreading client requests
    private final Socket client;
    private ArrayList<ArrayList<String>> questions;
    private final String test;
    private String studentName;

    // Constructor
    public ClientHandler(Socket socket, String test, ArrayList<ArrayList<String>> questions) {
        this.client = socket;
        this.test = test;
        this.questions = questions;
    }

    @Override
    public void run() {
        // init
        BufferedReader input = null;
        PrintWriter w = null;
        // create input/output stream
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            w = new PrintWriter(client.getOutputStream());
            // input, output ready
            w.println("connected");
            w.flush();
            // get that students name
            studentName = input.readLine();
            System.out.println("new student connected: " + studentName);
            // tell them the name of quiz
            w.println(test);
            w.flush();
            int total = 0;
            int correct = 0;
            boolean isTest = false;
            for (ArrayList<String> q : questions) {
                w.println(q.get(0));// display q's
                w.flush();
                for (int i = 1; i < q.size(); i++) {
                    String send = q.get(i);
                    if (send.charAt(0) == '!') {
                        send = send.substring(1);
                        isTest = true;
                    }
                    send = i + ". " + send;
                    w.println(send);
                    w.flush();
                }
                if (!isTest) {
                    correct++;
                }
                w.println("iosuhefiuherfiushzfgiu");
                w.flush();
                // accept answer
                int ansIndex = Integer.parseInt(input.readLine());
                // check for correct
                if (q.get(ansIndex).charAt(0) == '!') {
                    correct++;
                    total++;
                } else {
                    total++;
                }
            }
            // send result
            double c = (double) correct;
            double t = (double) total;
            double score = c / t;
            DecimalFormat percent = new DecimalFormat("#0.00 %");
            w.println("Your score was : " + percent.format(score));
            w.println("exit");
            w.flush();
            // save result
            try (FileWriter fw = new FileWriter("Content/"+test+"_results.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(studentName+" "+score);
            } catch (IOException e) {
                // exception handling left as an exercise for the reader
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // if (output !=null){
                // output.close();
                // }
                if (input != null) {
                    input.close();
                }
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
