package SocketTcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
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
			System.out.println("客服端连接成功:"+ socket.hashCode());
			
			
			while (true) {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br.readLine();
				System.out.println("客服端发过来的消息:" + mass + "\n");
				
				
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("服务端返回的消息："+mass );
				bw.newLine();
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 当不等于空的时候关闭连接
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