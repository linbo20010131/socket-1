package OneUpload.method5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 
 * @author liuCong
 * 
 * @data 2017年8月23日
 */
public class Server {
	public static void main(String[] args) {
		Server server = new Server();
		String filePath = "F:\\";
		int port = 25420;// 定义一个端口,和客户端保持一致
		server.upload(filePath, port);

	}

	/**
	 * 服务端文件读取下载(写入)
	 *
	 * @param filePath 路径
	 * @param port     端口
	 */
	public void upload(String filePath, int port)  {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dataInput = null;

		try {
			serverSocket = new ServerSocket(port);// 构建一个Serversocket
			while (true) {
				socket = serverSocket.accept();// 监听客户端
				System.out.println("\n客户端" + socket.getInetAddress() + "已连接\n");
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