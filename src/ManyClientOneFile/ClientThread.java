package ManyClientOneFile;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    private String FileHeadPath;
    private String basePath;


    public ClientThread(String FileHeadPath, String basePath) {
        this.FileHeadPath = FileHeadPath;
        this.basePath = basePath;
    }


    @Override
    public void run() {
        try {
            socket = new Socket("192.168.1.55", 25420);
            dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            //�ļ�
            File file = new File(FileHeadPath);

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
            }
            if (file.isFile()) {
                dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                //����mark��ʾ��
                dataOutput.writeInt(2);
                dataOutput.flush();

                //�����ļ�·��
                String fileName = file.getPath().replace(basePath, "");
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
                    dataOutput.write(byt, 0, len);// ����ȡ���ļ�����д��ȥ
                    dataOutput.flush();
                }
                System.out.println("�ļ���" + fileName + "��ȡ�ɹ�");// ��ȡ�ɹ�
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Client.mark--;
                dataInput.close();
                dataOutput.close();
                socket.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

}