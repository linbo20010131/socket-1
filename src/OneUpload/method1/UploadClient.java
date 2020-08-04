package OneUpload.method1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));// ת���ɻ�����,����ȡ�����ݷ���һ��������
			// ��Socket����õ�������,��������Ӧ��BufferedReader����
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			
			
			char[] cha = new char[in.available()]; // newһ��char����available()��ʾ�������ݳ���
			int len = 0;
			while ((len = reader.read(cha)) != -1) { // ֻҪ�������ݳ��Ȳ�����-1,�ͼ�����ȡ
				String line = new String(cha, 0, len);// ����ȡ��������һ��String������ס
				output.write(line);// ������д��Socket�����������
				output.newLine();// д��һ���зָ�����
				output.flush();// ˢ����
			}
			/**
			 * �ر���
			 */
			output.close();
			reader.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}