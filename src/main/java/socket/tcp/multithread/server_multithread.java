package socket.tcp.multithread;

import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class server_multithread {
    public static int tcpServerPort = 9999;
    public static List<Socket> clientSockets = new ArrayList<>();
    public static PrintWriter out = null;
    public static void main(String[] args) throws IOException {
        ServerSocket ssk = null;
        Socket socket = null;
        try {
            ssk = new ServerSocket(tcpServerPort);
            System.out.println("> echo-server is activated");
            while (true) {
                socket = ssk.accept();
                clientSockets.add(socket);
                // client가 접속할때마다 새로운 스레드 생성
                MyThread newThread = new MyThread(socket);
                newThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ssk!=null) {
                try {
                    out.close();
                    ssk.close();
                    System.out.println("> echo-server is de-activated");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("> echo-server error");
                }
            }
        }
    }

    static class MyThread extends Thread{
        Socket socket;
        public MyThread(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            BufferedReader in = null;
            Scanner scanner = new Scanner(System.in);
            try{
                System.out.println("> client connect by IP address " + socket.getInetAddress() +
                        " with Port number "+socket.getPort());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String input = in.readLine();
                    if("quit".equalsIgnoreCase(input)){
                        clientSockets.remove(socket);
                        break;
                    }
                    System.out.println(">Client "+socket.getPort()+": " + input);
                    System.out.println(">");
                    String output = scanner.nextLine();
                    for(Socket client : clientSockets){
                        try{
                            System.out.println(client);
                            out = new PrintWriter(client.getOutputStream(),true);
                            out.println(output);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    in.close();
                    socket.close();
                }catch (IOException e){
                    System.out.println("> echo-server error");
                }
            }
        }
    }
}
