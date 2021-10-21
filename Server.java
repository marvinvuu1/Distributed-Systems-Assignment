
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
    public static void main(String[] args){
        ServerSocket sSocket; 
        Socket cSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner s = new Scanner(System.in);

        try{                               
            sSocket = new ServerSocket(7000); //instantiated the Server Socket
            cSocket = sSocket.accept();       //instantiated the Client Socket
            out = new PrintWriter(cSocket.getOutputStream()); //instantiated the output
            in = new BufferedReader(new InputStreamReader(cSocket.getInputStream())); //instantiated the input

            Thread send = new Thread(new Runnable() { //Creating the thread that sends the message to the client
                String msg; //the message
                @Override
                public void run(){
                    while(true){
                        msg = s.nextLine(); //read the next line for the msg
                        out.println(msg); //write the string from msg to the client socket
                        out.flush();      //Clear the out stream
                    }
                }
            });
            send.start();

            Thread receive = new Thread(new Runnable() { //Creating the thread for receiving messages from the client
                String msg; //the message
                @Override
                public void run(){
                    try {
                        msg = in.readLine(); //read the string from the buffered reader
                        while(msg != null){  //if the message is not empty it will continue to receive messages
                            System.out.println("Client: " + msg); //displays the clients message
                            msg = in.readLine();
                        }
                        System.out.println("Client: closed");
                        out.close(); 
                        cSocket.close();
                        sSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }






    
}