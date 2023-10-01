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
        ServerSocket ssk = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        BufferedReader in;
        PrintWriter out;
        try {
            //서버 생성
            ssk = new ServerSocket(tcpServerPort);
            System.out.println("> eco-server is activated");
            //Server is bind
            //Client 접속 accept
            socket = ssk.accept();
            System.out.println(socket);
            System.out.println("> client connect by IP address" + socket.getInetAddress() + " with Port number"
                    + socket.getPort());
            //Client가 보낸 데이터 출력
            while(true) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                String input = in.readLine();
                if ("quit".equalsIgnoreCase(input)) break;
                System.out.println(">Client " + socket.getPort() + ":" + input);
                System.out.print(">");
                String output = scanner.nextLine();
                out.println(output);
                out.flush();
                if ("quit".equalsIgnoreCase(output)) break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                scanner.close();
                socket.close();
                ssk.close();
                System.out.println("> echo-server is de-activated");
            }catch (IOException e){
                System.out.println("> echo-server error");
            }
        }
    }

}
