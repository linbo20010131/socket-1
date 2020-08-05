package OneUpload.method5;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private String filePath = null;
    private Socket socket = null;
    DataInputStream dataInput = null;
    DataOutputStream dataOutput = null;

    public ServerThread(Socket socket, String filePath) {
        this.socket = socket;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            //��ȡsocket������
            dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            /**
             * �õ��ļ���
             */
            int fileNameLength = 0;
            while (true) {
                if (dataInput.available() >= 4) {
                    fileNameLength = dataInput.readInt(); // �õ��ͻ���д�������ļ�����,������4���ֽ�
                    break;
                }
            }
            byte name[] = new byte[fileNameLength]; // ת����byte����
            int nameLength = 0;
            String fileName = null;
            while (true) {// ֱ�����ļ����ĳ���ȫ����ȡ���
                nameLength += dataInput.read(name);
                if (fileNameLength == nameLength) {
                    break;
                }
            }
            fileName = new String(name);

            /**
             * �õ��ļ�����
             */
            int fileTypeLength = dataInput.readInt();// �õ��ļ����͵ĳ���

            byte type[] = new byte[fileTypeLength];// ת����byte����
            int typelength = 0;
            String fileType = null;
            while (true) {
                typelength += dataInput.read(type);
                if (fileTypeLength == typelength) {
                    break;
                }
            }
            fileType = new String(type);// ��byte����ת����String �õ��ļ�����


            /**
             * �õ��ļ����ݵĳ���
             */
            long longLength = dataInput.readLong();// �õ��ͻ���д�������ļ����ݳ���

            /**
             * �õ��ϴ���
             */
            int uploaderNameLength = dataInput.readInt();// �õ��ϴ��ߵĵĳ���

            String uploader = null;
            int uploaderLength = 0;
            byte uploaderName[] = new byte[uploaderNameLength];// תΪbyte����

            while (true) {
                uploaderLength += dataInput.read(uploaderName);
                if (uploaderLength == uploaderNameLength) {
                    break;
                }
            }
            uploader = new String(uploaderName);// תΪString�õ��ϴ���name


            /**
             * ȡ������֮��Ҫ��ŵ�·��
             */
            filePath += fileName;// �õ�(�̷�+�ļ��� )����·��

            System.out.print("[�ļ���:" + fileName + " | ");
            System.out.print("�ļ�������:" + fileNameLength + " | ");
            System.out.print("�ļ�����:" + fileType + " | ");
            System.out.print("�ļ����ݳ���:" + longLength + " | ");
            System.out.print("�ϴ���:" + uploader + " | ");
            System.out.println("���·��:" + filePath + "]" +"\n");


            dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            byte byt[] = new byte[1024 * 1024 * 20];// ÿ�ζ�ȡ20M
            long sumLength = 0;
            int len = 0;

            double schedule = 0;
            double oldSchedule = 0;
            long startTime = System.currentTimeMillis(); // ���ؿ�ʼʱ��
            System.out.print("�ļ�������:[\n");
            while (true) {
                if (longLength == sumLength) {// ����ͻ���д�������ļ����ȵ�������Ҫ��ȡ���ļ����Ⱦͽ���ѭ��
                    break;
                }
                len = dataInput.read(byt);
                sumLength += len;
                dataOutput.write(byt, 0, len);
                schedule = Math.round(sumLength / (double) longLength * 100);// �������,���ȵ����ļ��ĳ��ȳ��Կͻ���д�����ĳ���*100ȡ��

                if (schedule > oldSchedule) {// ֻҪ���ؽ��ȴ���֮ǰ�Ľ���
                    oldSchedule = schedule;// ����ǰ���ȸ���֮ǰ�Ľ���
                    System.out.print("#");
                }
                dataOutput.flush();
            }
            System.out.println("]" + oldSchedule + "%\n");
            System.out.println("д��ɹ�");
            long endTime = System.currentTimeMillis();// ���ؽ���ʱ��
            long time = (endTime - startTime) / 1000;// �ܺ�ʱ
            System.out.println("����ʱ��:" + time + "s");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {// �ر�����

                if (null != dataOutput) {
                    dataOutput.close();
                }
                if (null != dataInput) {
                    dataInput.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
