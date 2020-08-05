package SocketTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 客户端
 * 
 * @author Administrator
 *
 */
public class SocketClient1 {
	
	public static void main(String[] args) throws Exception {
		for(int i = 0;i<50000000;i++)
		{
			Thread.sleep(10);
			Socket socket = null;
			
			try {
				// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
				socket = new Socket("192.168.1.110", 15222);
				new ClientThread(socket).start();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}
}