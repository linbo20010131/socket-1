package OneUpload.method5;

import java.io.*;
import java.net.Socket;

/**
 * �ļ��ϴ��ͻ���
 * 
 * @author liuCong
 * 
 * @data 2017��8��23��
 */
public class Client1 {
	public static void main(String[] args) throws InterruptedException {
		Client1 client1 = new Client1();
		File file = new File("F:\\linux\\linuxIso\\CentOS-6.8-x86_64-bin-DVD1.iso");
		client1.upload(file,"192.168.1.55",25420);


		Client1 client11 = new Client1();
		File file1 = new File("F:\\linux\\linuxIso\\CentOS-7-x86_64-DVD-1511.iso");
		client11.upload(file1,"192.168.1.55",25420);

	}

	/**
	 * �ͻ���1���ļ���ȡ�ϴ�
	 * 
	 * @param file
	 *            �ļ�
	 */
	public void upload(File file,String ip,int port) {
		Socket socket = null;

		try {
			//��ͷ��Э��ͷ
			socket = new Socket(ip, port);// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
			new ClientThread(socket,file).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}