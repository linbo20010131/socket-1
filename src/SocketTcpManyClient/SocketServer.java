package SocketTcpManyClient;

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
		
		try {
			serverSocket = new ServerSocket(15222);
			while(true)
			{
				socket = serverSocket.accept();
				System.out.println("��ǰ�߳�����:  " + Thread.activeCount());
				new ServerThread(socket).start();
				
				
				//����̴߳���
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}