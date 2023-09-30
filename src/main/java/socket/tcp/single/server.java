package socket.tcp.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class server {
    public static int tcpServerPort = 9999;
    public static void main(String[] args) throws IOException {
        try {
            //서버 생성
            ServerSocket ssk = new ServerSocket(tcpServerPort);
            System.out.println("> eco-server is activated");
            Scanner scanner = new Scanner(System.in);
            //Server is bind
            while(true){
                //Client 접속 accept
                Socket socket = ssk.accept();
                System.out.println(socket);
                System.out.println("> client connect by IP address" + socket.getInetAddress() + "with Port number"
                        + socket.getPort());
                //Client가 보낸 데이터 출력
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String input = in.readLine();
                    if ("quit".equalsIgnoreCase(input)) {
                        socket.close();
                        break;
                    }
                    System.out.println(">Client: " + socket.getPort() + input);
                }
                System.out.print(">");
                String message = scanner.nextLine();
                if("quit".equalsIgnoreCase(message)) break;
            }
            ssk.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
