package OneUpload.method5;

import java.io.*;
import java.net.Socket;

/**
 * 文件上传客户端
 * 
 * @author liuCong
 * 
 * @data 2017年8月23日
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
	 * 客户端1的文件读取上传
	 * 
	 * @param file
	 *            文件
	 */
	public void upload(File file,String ip,int port) {
		Socket socket = null;

		try {
			//包头，协议头
			socket = new Socket(ip, port);// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
			new ClientThread(socket,file).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}