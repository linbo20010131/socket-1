package MangClientManyFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client1 {
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    public static String basePath = "F:\\����\\webService";


    public static void main(String[] args) throws Exception {



        SendFile(basePath);
       Thread.sleep(1000*600);
        System.out.println("�������");
    }



    public static void SendFile(String FileHeadPath) throws Exception {
        socket = new Socket("192.168.1.55", 25420);
        new ClientThread(socket,basePath).start();
        //��ȡsocket�����

    }

}
