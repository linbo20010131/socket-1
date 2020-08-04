package SocketTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private BufferedWriter bw = null;
	private BufferedReader br = null;
	private Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {

			while (true) {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br.readLine();
				System.out.println(">>Client:" + mass + "\n");

				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write(mass + "==================");
				bw.newLine();
				bw.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {

			}
		}

	}
}
