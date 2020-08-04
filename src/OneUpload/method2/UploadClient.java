package OneUpload.method2;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * �ļ��ϴ��ͻ���
 * 
 * @author liuCong
 * 
 */
public class UploadClient {
	public static void main(String[] args) {
		UploadClient client = new UploadClient();
		String filePath = "H:\\zlj\\linux\\linx����.txt";// Ҫ��ȡ���ļ�·��
		client.uploadRead(filePath);
	}

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * 
	 */
	public void uploadRead(String filePath) {
		try {
			Socket socket = new Socket("192.168.1.110", 25520);// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
			InputStream in = new FileInputStream(filePath);// ����һ�������
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filePath));// ת���ɻ�����,����ȡ�����ݷ���һ��������
			
			// ��Socket����õ�������,��������Ӧ��BufferedReader����
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			byte b[]= new byte[1024 * 1024 * 10];
			
			int length = 0;
			while ((length = dataInputStream.read(b)) != -1) {
				dataOutputStream.write(b, 0, length);
				dataOutputStream.flush();
			}
			
			
			/**
			 * �ر���
			 */
			dataOutputStream.close();
			dataInputStream.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}