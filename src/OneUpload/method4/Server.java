package OneUpload.method4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �����
 * 
 * @author liuCong
 * 
 * @data 2017��8��23��
 */
public class Server {
	public static void main(String[] args) {
		Server server = new Server();
		String filePath = "H:\\zlj\\linux\\s\\";
		int port = 25420;// ����һ���˿�,�Ϳͻ��˱���һ��
		server.upload(filePath, port);

	}

	/**
	 * ������ļ���ȡ����(д��)
	 * 
	 * @param path
	 *            ·��
	 * @param port
	 *            �˿�
	 */
	public void upload(String filePath, int port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dataInput = null;
		DataOutputStream dataOutput = null;

		try {
			serverSocket = new ServerSocket(port);// ����һ��Serversocket
			socket = serverSocket.accept();// �����ͻ���
			System.out.println("�ͻ���" + socket.getInetAddress() + "������");
			dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			/**
			 * �õ��ļ���
			 */
			int fileNameLength  = 0 ;
			while(true)
			{
				if(dataInput.available() >= 4)
				{
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
			dataInput.read(type);// ��ȡbtye����
			String fileType = new String(type);// ��byte����ת����String �õ��ļ�����

			/**
			 * �õ��ļ����ݵĳ���
			 */
			long longLength = dataInput.readLong();// �õ��ͻ���д�������ļ����ݳ���

			/**
			 * �õ��ϴ���
			 */
			int uploaderNameLength = dataInput.readInt();// �õ��ϴ��ߵĵĳ���
			byte uploaderName[] = new byte[uploaderNameLength];// תΪbyte����
			dataInput.read(uploaderName);// ��ȡbyte����
			String uploader = new String(uploaderName);// תΪString�õ��ϴ���name

			/**
			 * ȡ������֮��Ҫ��ŵ�·��
			 */
			filePath += fileName;// �õ�(�̷�+�ļ��� )����·��

			System.out.print("[�ļ���:" + fileName + " | ");
			System.out.print("�ļ�������:" + fileNameLength + " | ");
			System.out.print("�ļ�����:" + fileType + " | ");
			System.out.print("�ļ����ݳ���:" + longLength + " | ");
			System.out.print("�ϴ���:" + uploader + " | ");
			System.out.println("���·��:" + filePath + "]");

			dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			byte byt[] = new byte[1024 * 1024 * 20];// ÿ�ζ�ȡ20M
			long sumLength = 0;
			int len = 0;

			double schedule = 0;
			//double oldSchedule = 0;
			long startTime = System.currentTimeMillis(); // ���ؿ�ʼʱ��
			System.out.print("�ļ�������:[");
			while (true) {
				if (longLength == sumLength) {// ����ͻ���д�������ļ����ȵ�������Ҫ��ȡ���ļ����Ⱦͽ���ѭ��
					break;
				}
				len = dataInput.read(byt);
				sumLength += len;
				dataOutput.write(byt, 0, len);
				schedule = (sumLength / (double) longLength * 100);// �������,���ȵ����ļ��ĳ��ȳ��Կͻ���д�����ĳ���*100ȡ��
				System.out.println(schedule + "%");
				/*if (schedule > oldSchedule) {// ֻҪ���ؽ��ȴ���֮ǰ�Ľ���
					oldSchedule = schedule;// ����ǰ���ȸ���֮ǰ�Ľ���
					System.out.print("#");
				}*/
				dataOutput.flush();
			}
			//System.out.println("]" + oldSchedule + "%");
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
				if (null != socket) {
					socket.close();
				}
				if (null != serverSocket) {
					serverSocket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}