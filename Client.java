
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    public static void main(String[] args){
        Socket cSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner s = new Scanner(System.in);

        try {
            cSocket = new Socket("127.0.0.1", 7000); //Connecting client socket
            out = new PrintWriter(cSocket.getOutputStream()); //connecting to output 
            in = new BufferedReader(new InputStreamReader(cSocket.getInputStream())); //connecting to input

            Thread send = new Thread(new Runnable() { //Creating thread to send messages to the server
                String msg;
                @Override
                public void run(){
                    while(true){
                        msg = s.nextLine(); //reading user input 
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            send.start();

            Thread receive = new Thread(new Runnable() { //Creating thread to receive messages from the server
                String msg;
                @Override
                public void run(){
                    try {
                        msg = in.readLine();
                        while(msg != null){ //while the message isn't empty, the client will keep receiving 
                            System.out.println("Server: " + msg);
                            msg = in.readLine();
                        }
                        System.out.println("Server is disconnected");
                        out.close();
                        cSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}