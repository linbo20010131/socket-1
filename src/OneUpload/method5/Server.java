package OneUpload.method5;

import java.io.*;
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
		String filePath = "F:\\";
		int port = 25420;// ����һ���˿�,�Ϳͻ��˱���һ��
		server.upload(filePath, port);

	}

	/**
	 * ������ļ���ȡ����(д��)
	 *
	 * @param filePath ·��
	 * @param port     �˿�
	 */
	public void upload(String filePath, int port)  {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dataInput = null;

		try {
			serverSocket = new ServerSocket(port);// ����һ��Serversocket
			while (true) {
				socket = serverSocket.accept();// �����ͻ���
				System.out.println("\n�ͻ���" + socket.getInetAddress() + "������\n");
				System.out.println(Thread.activeCount());
				System.out.println(socket.hashCode());
				new ServerThread(socket,filePath).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}