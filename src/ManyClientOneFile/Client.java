package ManyClientOneFile;

import java.io.*;
import java.net.Socket;



public class Client {
    static int mark = 1;
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static String basePath = "F:\\aaa";


    public static void main(String[] args) throws Exception {

        SendFile(basePath);
        System.out.println("发送完成");

    }


    public static void SendFile(String FileHeadPath) throws Exception {

        while (true) {
            if (mark > 5) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }

        File file = new File(FileHeadPath);
        if (file.isDirectory()) {
            //不发送基础目录
            if (!FileHeadPath.equals(basePath)) {
                mark++;
                ClientThread clientThread = new ClientThread(FileHeadPath,basePath);
                //启动
                clientThread.start();
                clientThread.join();
            }

            //递归继续发送
            String files[] = file.list();
            for (String filePath : files
            ) {
                SendFile(FileHeadPath + File.separator + filePath);
            }
        } else {
            mark++;
            new ClientThread(FileHeadPath, basePath).start();
        }


    }

}
