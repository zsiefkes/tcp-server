/*
 * This class has been copied from http://cs.lmu.edu/~ray/notes/javanetexamples/ 
 */

//package javasocketprogramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//import javax.swing.JOptionPane;

/**
 * Trivial client for the date server.
 */
public class DateClient {

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";/*JOptionPane.showInputDialog(
            "Enter IP Address of a machine that is\n" +
            "running the date service on port 9090:");*/
        int port = 9090;
        Socket s = new Socket(serverAddress, port);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        //JOptionPane.showMessageDialog(null, answer);
        System.out.println(answer);
        System.exit(0);
        s.close();
    }
}