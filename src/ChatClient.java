import java.net.*;
import java.io.*;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; // would be the computers IP that is acting as a server
    private static final int SERVER_PORT = 5000;//needs to match the servers port

    //color for messages to add some decoration
    private static final String BLUE = "\u001B[34m";   // your messages
    private static final String GREEN = "\u001B[32m";  // other users' messages
    private static final String YELLOW = "\u001B[33m"; // system messages
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args){

        try{
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);



            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter username: ");
            String username = input.readLine();
            out.println(username);

            new Thread(new ReceivedMessagesHandler(socket)).start(); //background thread to listen to any messages sent

            String message;
            while((message = input.readLine()) != null){ //assigns keyboards input as the message
                out.println(BLUE + message + RESET);


                //sends the typed message to the server

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static class ReceivedMessagesHandler implements Runnable{
        private Socket socket;

        public ReceivedMessagesHandler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try{

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //reads the message from the server

                String message;
                while((message = input.readLine()) != null){

                    if(message.contains("has joined the chat") || message.contains("has left the chat")){
                        System.out.println(YELLOW + message + RESET);
                    }else{

                        System.out.println(GREEN + message + RESET);

                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
