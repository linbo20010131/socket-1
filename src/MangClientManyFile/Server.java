package MangClientManyFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;


    public static void main(String[] args) throws IOException {

        String basePath = "F:\\Test\\";
        int port = 25420;// ����һ���˿�,�Ϳͻ��˱���һ��
        upload(basePath, port);
    }


    public static void upload(String basePath, int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            //�����ͻ�������
            socket = serverSocket.accept();
            new ServerThread(socket,basePath).start();
        }
    }
}
