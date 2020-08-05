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
public class Client2 {
	public static void main(String[] args) {
		Client2 client1 = new Client2();
		File file = new File("F:\\linux\\linuxIso\\CentOS-6.8-x86_64-bin-DVD1.iso");
		client1.upload(file);
	}

	/**
	 * �ͻ���1���ļ���ȡ�ϴ�
	 * 
	 * @param file
	 *            �ļ�
	 */
	public void upload(File file) {
		Socket socket = null;
		DataInputStream dataInput = null;
		DataOutputStream dataOutput = null;
		String uploaderName = "�ֲ�";
		try {
			//��ͷ��Э��ͷ
			socket = new Socket("192.168.1.55", 25420);// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
			dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// ��Socket����õ�������,��������Ӧ��DataOutputStream����

			
			
			String fileName = file.getName();// �õ��ļ���
			dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length()); // ���ļ����ĳ���ת��֮��(����ļ��������ĳ������������)д��ȥ
			dataOutput.write(fileName.getBytes());// ���ļ���д��ȥ
			dataOutput.flush();

			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// �õ��ļ�����
			dataOutput.writeInt(fileType.length());// ���ļ����͵ĳ���д��ȥ
			dataOutput.write(fileType.getBytes());// ���ļ�����д��ȥ
			dataOutput.flush();

			dataOutput.writeLong(file.length()); // �ļ����ݳ���д��ȥ
			dataOutput.flush();

			dataOutput.writeInt(new String(uploaderName.getBytes(),	"ISO-8859-1").length());// ���ϴ��ߵĳ���д��ȥ
			dataOutput.write(uploaderName.getBytes());// ���ϴ���д��ȥ
			dataOutput.flush();

			System.out.print("[�ļ���:" + fileName + " | ");
			System.out.print("�ļ�������:" + file.getName().length() + " | ");
			System.out.print("�ļ�����:" + fileType + " | ");
			System.out.print("�ļ����ݳ���:" + file.length() + " | ");
			System.out.print("�ϴ���:" + uploaderName + "]");

			byte[] byt = new byte[1024 * 1024 * 20];// newһ��byte����
			int len = 0;
			while ((len = dataInput.read(byt)) > 0) {// ֻҪ�ļ����ݵĳ��ȴ���0,��һֱ��ȡ

				dataOutput.write(byt, 0, len);// ����ȡ���ļ�����д��ȥ
				dataOutput.flush();
			}
			System.out.println("��ȡ�ɹ�");// ��ȡ�ɹ�
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != dataOutput) {
					dataOutput.close();
				}
				if (null != dataInput) {
					dataInput.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}