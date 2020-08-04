package socketUdp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * �����
 * 
 * @author liuCong
 * 
 * @data 2017��8��23��
 */
public class UDPServer {
	public static void main(String[] args) {
		DatagramSocket server = null;
		try {
			server = new DatagramSocket(1055);
			
			
			byte[] byt = new byte[1024];
			DatagramPacket packet = new DatagramPacket(byt, byt.length);
			while (true) {
				server.receive(packet);
				String str = new String(packet.getData(), 0, packet.getLength());
				System.out.println("�յ�:" + str);
				if (str.equals("exit")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				server.close();
			}
		}
	}
}