package OneUpload.method1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �ļ��ϴ������
 * 
 * @author liuCong
 * 
 */
public class UploadSever {
	public static void main(String[] args) {
		UploadSever server = new UploadSever();
		File file = new File("H:\\zlj\\linux\\linx����1.txt");// ����һ��File����
		server.UploadWriter(file);
	}

	/**
	 * д���ļ�
	 * 
	 * @param file
	 *            �ļ�·��
	 */
	public void UploadWriter(File file) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		OutputStream out = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			serverSocket = new ServerSocket(25520);
			socket = new Socket(); 
			socket = serverSocket.accept();

			
			
			out = new FileOutputStream(file);// ����һ���ļ�������
			bw = new BufferedWriter(new OutputStreamWriter(out));// ����һ��������,��Ҫд����ļ����ڻ���������
			// ��Socket����õ������,��������Ӧ��BufferedReader����
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			String str = null;
			while ((str = br.readLine()) != null) {// ֻҪ��ȡ�����ݲ����ڿ�,�ͻ�һֱ��ȡ
				bw.write(str );// ����ȡ�����ݻ���д��
				bw.flush();// ˢ����
			}
			System.out.println("д��ɹ�");
			/**
			 * �ر�����
			 */
			br.close();
			bw.close();
			out.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}