package MangClientManyFile;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    public static String basePath = "F:\\apache-tomcat-8.0.48";


    public static void main(String[] args) throws Exception {



        SendFile(basePath);
       // SendFile("F:\\Download");
        Thread.sleep(1000*60);
        System.out.println("发送完成");

    }



    public static void SendFile(String FileHeadPath) throws Exception {
        socket = new Socket("192.168.1.55", 25420);
        new ClientThread(socket,basePath).start();
        //获取socket输出流

    }

}
