package socket.tcp.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client_multithread {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("127.0.0.1",9999);
            System.out.println(">echo-client is activated with port number"+socket.getLocalPort());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            while(true){
                System.out.println(">");
                String output = scanner.nextLine();
                out.println(output);
                out.flush();
                if("quit".equalsIgnoreCase(output)) break;
                String input = in.readLine();
                if("quit".equalsIgnoreCase(input)) break;
                System.out.println(">Server " +socket.getPort()+":"+ input);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                scanner.close();
                socket.close();
                System.out.println("> echo-client is de-activated");
            }catch (IOException e){
                System.out.println("> echo-client error");
            }
        }
    }
}
