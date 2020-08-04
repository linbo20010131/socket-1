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
			// 由系统输入并构造BufferedReader对象
			br = new BufferedReader(new InputStreamReader(System.in));
			// 由Socket对象得到输入流,并构造相应的BufferedReader对象
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) {// 循环输入
				//System.out.println("请输入消息：");
				bw.write(br.readLine());// 将从系统输入读入的字符输出到Service
				bw.newLine();// 写入一个换行空格
				bw.flush();// 刷新

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
