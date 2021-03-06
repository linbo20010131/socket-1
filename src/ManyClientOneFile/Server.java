package ManyClientOneFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;




    public static void main(String[] args) throws Exception {

        String basePath = "F:\\Test\\";
        int port = 25420;// 定义一个端口,和客户端保持一致
        upload(basePath, port);
    }


    public static void upload(String basePath, int port) throws Exception {
        serverSocket = new ServerSocket(port);
        while (true) {
            socket = serverSocket.accept();
            System.out.println("客户端:" + socket.getInetAddress());
            new ServerThread(basePath, socket).start();
        }


    }
}
