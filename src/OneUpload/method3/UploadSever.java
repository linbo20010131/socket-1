package OneUpload.method3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
		File file = new File("H:\\zlj\\linux\\s");// ����һ��File����
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
		DataOutputStream bw = null;
		DataInputStream br = null;
		try {
			serverSocket = new ServerSocket(25520);
			socket = new Socket(); 
			socket = serverSocket.accept();

			
			
			// ��Socket����õ������,��������Ӧ��BufferedReader����
			br = new DataInputStream(socket.getInputStream());
			
			String filename = br.readUTF();
			file = new File(file.getPath() + File.separator +filename);
			
			bw = new DataOutputStream(new FileOutputStream(file));// ����һ��������,��Ҫд����ļ����ڻ���������
			
			byte b[]= new byte[1024 * 1024 * 10];
			int length = 0;
			while ((length = br.read(b)) != -1) {
				bw.write(b, 0, length);
				bw.flush();
			}
			
			/**
			 * �ر�����
			 */
			br.close();
			bw.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}