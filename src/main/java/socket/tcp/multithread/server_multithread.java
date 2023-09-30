package socket.tcp.multithread;

import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server_multithread {
    public static int tcpServerPort = 9999;
    public static Scanner scanner;
    public static void main(String[] args) throws IOException {
        ServerSocket ssk = null;
        Socket socket = null;
        try {
            ssk = new ServerSocket(tcpServerPort);
            System.out.println("> echo-server is activated");
            while (true) {
                socket = ssk.accept();
                // client가 접속할때마다 새로운 스레드 생성
                MyThread newThread = new MyThread(socket);
                newThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ssk!=null) {
                try {
                    ssk.close();
                    System.out.println("> echo-server is de-activated");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("> echo-server error");
                }
            }
        }
    }
}

class MyThread extends Thread{
    Socket socket;
    public MyThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try{
            System.out.println("> client connect by IP address" + socket.getInetAddress() +
                    "with Port number "+socket.getPort());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String input = br.readLine();
                if("quit".equalsIgnoreCase(input)) {
                    socket.close();
                    break;
                }
                System.out.println();
                System.out.println(">Client "+socket.getPort()+": " + input);
            }
        }catch (Exception e){
            System.out.println("예외가 발생했습니ㅏㄷ");
        }
    }
}