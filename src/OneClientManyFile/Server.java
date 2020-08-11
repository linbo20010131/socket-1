package OneClientManyFile;

import javax.xml.soap.SOAPConnection;
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
            dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            while (true) {
                int mark = 0;
                //�ļ��洢Ŀ¼
                String filePath = basePath;
                //��ȡ��������markֵ
                while (true) {
                    if (dataInput.available() >= 4) {
                        mark = dataInput.readInt(); //
                        break;
                    }
                }
                //���mark 1 �ļ��� 2 �ļ�
                if (mark == 1) {
                    //��ȡ�ļ�·������
                    int filePathLength = 0;
                    while (true) {
                        if (dataInput.available() >= 4) {
                            filePathLength = dataInput.readInt(); // �õ��ͻ���д�������ļ�����,������4���ֽ�
                            break;
                        }
                    }
                    //��ȡ�ļ�·������
                    byte[] bytes = new byte[filePathLength];
                    int pathLength = 0;
                    String pathName = null;
                    while (true) {// ֱ�����ļ����ĳ���ȫ����ȡ���
                        pathLength += dataInput.read(bytes);
                        if (filePathLength == pathLength) {
                            break;
                        }
                    }
                    pathName = new String(bytes);
                    //�ļ��д洢��·��
                    filePath += pathName;
                    System.out.println(filePath);

                    File file = new File(filePath);
                    //����ļ��в����ڣ��ʹ���
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    //�����ļ�
                } else if (mark == 2) {
                    //��ȡ�ļ�������
                    int fileNameLength = 0;
                    while (true) {
                        if (dataInput.available() >= 4) {
                            fileNameLength = dataInput.readInt();
                            break;
                        }
                    }

                    //��ȡ�ļ�·��
                    byte[] bytes = new byte[fileNameLength];
                    int nameLength = 0;
                    String fileName = null;
                    while (true) {
                        nameLength += dataInput.read(bytes);
                        if (nameLength == fileNameLength) {
                            break;
                        }
                    }
                    fileName = new String(bytes);

                    //��ȡ�ļ����ͳ���
                    int fileTypeLength = 0;
                    while (true) {
                        if (dataInput.available() >= 4) {
                            fileTypeLength = dataInput.readInt();
                            break;
                        }
                    }
                    //��ȡ�ļ�����
                    byte[] bytes1 = new byte[fileTypeLength];
                    int fileTypeNameLength = 0;
                    String fileType = null;
                    while (true) {
                        fileTypeNameLength += dataInput.read(bytes1);
                        if (fileTypeNameLength == fileTypeLength) {
                            break;
                        }
                    }
                    fileType = new String(bytes1);

                    //��ȡ�ļ�����
                    long longLength = dataInput.readLong();

                    //�ļ��洢λ��
                    filePath += fileName;

                    System.out.print("[�ļ���:" + fileName + " | ");
                    System.out.print("�ļ�������:" + fileNameLength + " | ");
                    System.out.print("�ļ�����:" + fileType + " | ");
                    System.out.print("�ļ����ݳ���:" + longLength + " | ");
                    System.out.println("���·��:" + filePath + "]" );
                    File file = new File(filePath);
                    //����ļ������ھʹ���
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));

                    byte byt[] = new byte[1024 * 1024 * 20];// ÿ�ζ�ȡ20M
                    //�ۼӳ���
                    long sumLength = 0;
                    int len = 0;
                    while (true) {

                        if(longLength == sumLength){
                            break;
                        }
                        //����ܳ���-�ۼӳ��� ���ڿɶ�����

                        //1000 920           80        100
                        if(longLength - sumLength <dataInput.available()){
                            //��ȡ�ļ�ʣ����ֽ�
                            len = dataInput.read(byt,0,Integer.valueOf(String.valueOf(longLength - sumLength)));
                        }else{
                            //ʣ�೤��С�� �ɶ�
                            if(longLength -sumLength >(1024*1024*20)){
                                len = dataInput.read(byt);
                            }else {
                                len = dataInput.read(byt,0,Integer.valueOf(String.valueOf(longLength - sumLength)));
                            }
                        }
                        sumLength += len;
                        dataOutput.write(byt,0,len);
                        dataOutput.flush();



                    }
                    System.out.println("д��ɹ�");
                    //�ر�dataOutput
                    dataOutput.close();
                }

                //if(dataInput.){
               //    break;
               // }

            }
        }


    }
}
