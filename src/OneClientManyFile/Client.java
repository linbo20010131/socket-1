package OneClientManyFile;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static  String basePath = "F:\\Program Files";


    public static void main(String[] args) throws Exception {

        socket = new Socket("192.168.1.55", 25420);
        //��ȡsocket�����
        dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        SendFile(basePath);
        System.out.println("�������");
        while (true){

        }



    }


    public static void SendFile(String FileHeadPath) throws Exception {

        //���ݻ���·����ȡ�ļ����ļ���
        String files[] = new File(FileHeadPath).list();
        for (String filePath : files) {
            //�ļ�
            File file = new File(FileHeadPath + File.separator + filePath);
            //������
            //�ж�·�����ļ������ļ��� 1 �ļ���  2 �ļ�
            if (file.isDirectory()) {
                dataOutput.writeInt(1);
                dataOutput.flush();

                //��ȡ�ļ��е�·��
                String path = file.getPath().replace(basePath, "");
                System.out.println(path);

                //�����ļ���·������
                dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
                //�����ļ���·������
                dataOutput.write(path.getBytes());
                dataOutput.flush();

                //�ݹ��������

               // String files1[] = file.list();
              //  for (String files1Path:files1
                 //    ) {
                    SendFile(FileHeadPath + File.separator + filePath);
              //  }



            }
            if (file.isFile()) {
                dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                //����mark��ʾ��
                dataOutput.writeInt(2);
                dataOutput.flush();

                //�����ļ�·��
                String fileName = file.getPath().replace(basePath,"");
                dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length()); // ���ļ����ĳ���ת��֮��(����ļ��������ĳ������������)д��ȥ
                dataOutput.write(fileName.getBytes());// ���ļ���д��ȥ
                dataOutput.flush();


                //�����ļ�����
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// �õ��ļ�����
                dataOutput.writeInt(fileType.length());// ���ļ����͵ĳ���д��ȥ
                dataOutput.write(fileType.getBytes());// ���ļ�����д��ȥ
                dataOutput.flush();


                //�ļ�����
                dataOutput.writeLong(file.length()); // �ļ����ݳ���д��ȥ
                dataOutput.flush();

                byte[] byt = new byte[1024 * 1024 * 20];// newһ��byte����
                int len = 0;
                while ((len = dataInput.read(byt)) > 0) {// ֻҪ�ļ����ݵĳ��ȴ���0,��һֱ��ȡ
                    dataOutput.write(byt,0, len);// ����ȡ���ļ�����д��ȥ
                    dataOutput.flush();
                }
                System.out.println("�ļ���" + fileName + "��ȡ�ɹ�");// ��ȡ�ɹ�
                dataInput.close();


            }

        }



    }

}
