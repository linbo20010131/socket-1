package SocketTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	BufferedReader br = null;
	BufferedReader br1 = null;
	BufferedWriter bw = null;

	private Socket socket = null;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// ��ϵͳ���벢����BufferedReader����
			br = new BufferedReader(new InputStreamReader(System.in));
			// ��Socket����õ�������,��������Ӧ��BufferedReader����
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) {// ѭ������
				//System.out.println("��������Ϣ��");
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
			try {
				if (null != bw) {
					bw.close();
				}
				if (null != br) {
					br.close();
				}
			} catch (Exception e) {

			}
		}
	}
}
