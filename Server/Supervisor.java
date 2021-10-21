package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Supervisor {
    final static int PORT_NUMBER = 1234;
    final static String[] USERS = { "admin" };
    final static String[] PASSWORDS = { "admin" };
    final static Scanner sc = new Scanner(System.in);

    static String currentTest = "";
    static ArrayList<ArrayList<String>> currQuestions = new ArrayList<>();
    static boolean startTest = false;

    static ArrayList<Double> scores = new ArrayList<>();

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
        // verify login
        if (!login) {
            System.out.println("Incorrect username or password");
            System.out.println("Usage: java Server.Supervisor username password");
            System.exit(0);
        }

        System.out.println("Welcome, " + username + "!");
        displayMenu();

        if (startTest) {
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
                    // new thread
                    ClientHandler newThread = new ClientHandler(newStudent, currentTest, currQuestions);
                    new Thread(newThread).start();
                }
            } catch (Exception e) {
                System.out.println("Connection to student terminated");
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

    private static void displayMenu() {
        System.out.println("1. Create New Test");
        System.out.println("2. Accept Test Submissions");
        System.out.println("3. Review Test Stats");
        System.out.println("4. Delete a Test");
        System.out.println("5. Exit");
        System.out.println("please enter a number from the above selection");
        int input = 0;
        try {
            input = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid entry : no integer detected, try again");
            displayMenu();
            input = 0;
        }
        switch (input) {
        case 0:
            // do nothing
            break;
        case 1:
            // create test and store in local file under Content/
            createTest();
            break;
        case 2:
            // read available tests and get admin to select one
            showTestMenu(false);
            break;
        case 3:
            // show test menu with stats instead of everytrhing else
            showTestMenu(true);
        case 4:
            // delete test
            // deleteTest();
            break;
        case 5:
            System.exit(0);
            break;
        default:
            System.out.println("Invalid entry : please enter a number from the above selection");
            displayMenu();
            break;
        }
    }

    private static void createTest() {
        ArrayList<ArrayList<String>> questions = new ArrayList<>();
        System.out.print("What would you like to name the Test / Poll: ");
        String testName = sc.nextLine();
        System.out.print("Would you like to add a question? (y/n) ");
        while ("y".equalsIgnoreCase(sc.nextLine())) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(sc.nextLine());
            System.out.print("Would you like to add an answer to this question? (y/n) ");
            while ("y".equalsIgnoreCase(sc.nextLine())) {
                System.out.println("please add a '!' to the beginning if this is the correct answer");
                temp.add(sc.nextLine());
                System.out.print("Would you like to add an answer to this question? (y/n) ");
            }
            questions.add(temp);
            System.out.print("Would you like to add a question? (y/n) ");
        }
        // add to a file
        String path = "Content/" + testName + ".txt";
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                // write to file
                FileWriter wr = new FileWriter(path);
                // loop questions
                for (ArrayList<String> QA : questions) {
                    // write question
                    wr.append(QA.get(0) + "\n");
                    // write number of answers for question
                    wr.append((QA.size() - 1) + "\n");
                    // write answers
                    for (int i = 1; i < QA.size(); i++) {
                        wr.append(QA.get(i) + "\n");
                    }
                }

                wr.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("Test / Poll Created : " + testName);
    }

    private static void showTestMenu(boolean statsOnly) {
        String list = "";
        File availFileFolder = new File("Content");
        File[] listOfFiles = availFileFolder.listFiles();
        String prevFile="";
        for (File file : listOfFiles) {
            String currFile=file.getName().substring(0, file.getName().length() - 4).split("_")[0];
            if (!currFile.equals(prevFile)){
            list += currFile + " \n ";
            }
            prevFile=currFile;
        }
        System.out.println("Please enter the name of the test that you would like to use:\n" + list);
        loadTest("Content/" + sc.nextLine() + ".txt");
        System.out.println("Test Loaded : " + currentTest);
        if(!statsOnly){startTest = true;}
        printStats();

    }
    private static void printStats() {
        // read current test stat file and take note of average, low and high marks
        DecimalFormat percent=new DecimalFormat("##0.00 %");
        try {
            File file = new File("Content/"+currentTest+"_results.txt");
            Scanner r = new Scanner(file);
            double sum=0;
            int count=0;
            double low=2;
            double high=-1;
            while (r.hasNextLine()) {
                double entry=Double.parseDouble(r.nextLine().split(" ")[1]);
                //sum and count for average
                sum+=entry;
                count++;
                //check for new low
                if(entry<low){
                    low=entry;
                }
                //check for new high
                if(entry>high){
                    high=entry;
                }
            }
            if (low==2){
                System.out.println("This file is empty");
            }else{
                double avg=sum/count;
                System.out.println("Number of attempts overall: "+count);
                System.out.println("Average Score: "+percent.format(avg));
                System.out.println("Highest Score: "+percent.format(high));
                System.out.println("Lowest Score: "+percent.format(low));

            }
            
            r.close();
        } catch (Exception e) {
            System.out.println("There are no statistics for this test");
            e.printStackTrace();
        }
    }

    private static void loadTest(String path) {
        // re-init loaded questions
        currQuestions = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner r = new Scanner(file);
            currentTest = file.getName().substring(0, file.getName().length() - 4);

            while (r.hasNextLine()) {
                // read each question
                ArrayList<String> temp = new ArrayList<>();
                temp.add(r.nextLine());
                int numOfA = Integer.parseInt(r.nextLine());
                for (int i = 0; i < numOfA; i++) {
                    temp.add(r.nextLine());
                }
                currQuestions.add(temp);
            }
            r.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}