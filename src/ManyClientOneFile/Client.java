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
        System.out.println("�������");

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
            //�����ͻ���Ŀ¼
            if (!FileHeadPath.equals(basePath)) {
                mark++;
                ClientThread clientThread = new ClientThread(FileHeadPath,basePath);
                //����
                clientThread.start();
                clientThread.join();
            }

            //�ݹ��������
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
