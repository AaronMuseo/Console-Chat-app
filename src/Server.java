import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

    //server will listen on this port
    private static final int PORT = 5000;

    //allows broadcast of messages -> stores output streams to all connected clients
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Map<String, PrintWriter> clientNames = new HashMap<>();

    public static void main(String[] args){

        System.out.println("Server is running...");

        //ServerSocket listens on the parameter PORT which is set to 5000
        try(ServerSocket serverSocket = new ServerSocket(PORT)){

            while(true){
                Socket socket = serverSocket.accept();

                new ClientHandler(socket).start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static class ClientHandler extends Thread{
        private Socket socket; //connected client
        private PrintWriter out; //to send messages
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket){
            this.socket = socket;

            try{
                out = new PrintWriter(socket.getOutputStream(), true);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                synchronized(clientWriters){
                    clientWriters.add(out);
                    //add clients output stream to list of all clients
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        public void run(){

            try{
                clientName = in.readLine();
                synchronized(clientWriters){
                    clientNames.put(clientName, out);
                }

                System.out.println(clientName + " has joined the chat!");

                synchronized(clientWriters){
                    for(PrintWriter client : clientWriters){
                        if(client != out){
                            client.println(clientName + " has joined the chat!");
                        }
                    }
                }


                String message;

                while((message = in.readLine()) != null){

                    System.out.println(clientName + ": " + message);

                    synchronized(clientWriters){
                        for(PrintWriter client : clientWriters){
                            if(client != out){
                                client.println(clientName + ": "+ message);
                            }
                           // send message to every client connected
                        }
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                try{
                    socket.close();//closes the clients socket when its done
                }catch(IOException e){
                    e.printStackTrace();
                }

                synchronized(clientWriters){
                    clientWriters.remove(out);
                    clientNames.remove(out);
                }

                System.out.println(clientName + " has left the chat!");
                synchronized(clientWriters){
                    for(PrintWriter client : clientWriters){
                        client.println(clientName + " has left the chat!");
                    }
                }
            }
        }
    }
}
