package socketUdp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * �ͻ���
 * 
 * @author liuCong
 * 
 * @data 2017��8��23��
 */
public class UDPClient {
	public static void main(String[] args) {
		DatagramSocket client = null;
		try {
			client = new DatagramSocket();
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			int port = 1055;
			byte[] sendBuff;

			while (true) {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.println("������Ҫ���͵�����");
				String str = sc.nextLine();
				sendBuff = str.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuff,
						sendBuff.length, addr, port);
				client.send(sendPacket);
				if (str.equals("exit")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
}