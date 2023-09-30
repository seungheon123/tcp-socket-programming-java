package socket.tcp.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            System.out.println(">echo-client is activated with port number"+socket.getLocalPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(">");
                String output = scanner.nextLine();
                out.println(output);
                out.flush();
                if("quit".equalsIgnoreCase(output)) break;
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
