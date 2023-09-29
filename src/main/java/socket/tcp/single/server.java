package socket.tcp.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
    public static int tcpServerPort = 9999;
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket ssk = new ServerSocket(tcpServerPort);
            System.out.println("> eco-server is activated");
            Socket socket = ssk.accept();
            System.out.println("> client connect by IP address" + socket.getInetAddress() + "with Port number"
                    + socket.getPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String input = in.readLine();
                if("quit".equalsIgnoreCase(input)) break;
                System.out.println(">Client: "+input);
            }
            socket.close();
            ssk.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
