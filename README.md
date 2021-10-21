# DSYS_A1

Distributed Systems Assignment 1  

The application that I have decided to have as my assignment is a testing application. The supervisor server will ask a multiple choice question and also give possible answers. The subjects who will take the test (clients) will answer the questions as they are sent out asynchronously from the supervisor. The supervisor can create custom tests as well as view the statistics for any test with more than one attempt. The tests and test results are saved in the Content folder and each test can also be deleted along with its results file.
  
Instructions to Download:  

<ol>
<li>Open the desired download location in the terminal.</li>
<li>Run command "git clone https://github.com/UOITEngineering2/assignment1fall2020-OwaisQuadri" or "git clone https://github.com/OwaisQuadri/DSYS_A1".</li>
</ol>  
  
Instructions to open a new Supervisor session:  
<ol>
<li>Open a new terminal in "DSYS_A1" directory</li>
<li>Run command "java Server.Supervisor username password" with an authorized username and password.</li>
</ol>  
for now, use the username "admin" and password "admin"  
  
Instructions to open a new Student session:  
<ol>
<li>Open a new terminal in "DSYS_A1" directory.</li>
<li>Run command "java Client.Student username password" with an authorized username and password.</li>
</ol>  
for now, use the username "owais" and password "owais"