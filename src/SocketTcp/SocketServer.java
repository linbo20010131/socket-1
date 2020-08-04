package SocketTcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �����
 * 
 * @author Administrator
 *
 */
public class SocketServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			serverSocket = new ServerSocket(15222);
			socket = serverSocket.accept();
			System.out.println("�ͷ������ӳɹ�:"+ socket.hashCode());
			
			
			while (true) {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br.readLine();
				System.out.println("�ͷ��˷���������Ϣ:" + mass + "\n");
				
				
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("����˷��ص���Ϣ��"+mass );
				bw.newLine();
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �������ڿյ�ʱ��ر�����
			if (null != br) {
				br.close();
			}
			if (null != socket) {
				socket.close();
			}
			if (null != serverSocket) {
				serverSocket.close();
			}
		}
	}
}