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
public class SocketClient3 {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		BufferedReader br = null;
		BufferedReader br1 = null;
		BufferedWriter bw = null;
		try {
			// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
			socket = new Socket("192.168.1.110", 15222);
			socket.setSoTimeout(10000);// ��ֵ��ʱʱ��,����������ʱ��,�Զ��ر�����
			// ��ϵͳ���벢����BufferedReader����
			br = new BufferedReader(new InputStreamReader(System.in));
			// ��Socket����õ�������,��������Ӧ��BufferedReader����
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while (true) {// ѭ������
				System.out.println("��������Ϣ��");
				bw.write(br.readLine());// ����ϵͳ���������ַ������Service
				bw.newLine();// д��һ�����пո�
				bw.flush();// ˢ��
				
				
				br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br1.readLine();
				System.out.println(">>server:" + mass + "\n");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �������ڿյ�ʱ��ر�����
			if (null != bw) {
				bw.close();
			}
			if (null != br) {
				br.close();
			}
			if (null != socket) {
				socket.close();
			}
		}
	}
}