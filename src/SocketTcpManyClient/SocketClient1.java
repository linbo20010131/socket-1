package SocketTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * �ͻ���
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
				// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
				socket = new Socket("192.168.1.110", 15222);
				new ClientThread(socket).start();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}
}