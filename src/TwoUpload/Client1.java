package TwoUpload;

import java.io.File;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �ļ��ϴ��ͻ���
 *
 * @author liuCong
 * @data 2017��8��23��
 */
public class Client1 {
    public static void main(String[] args) throws InterruptedException {

        File file = new File("F:\\linux\\linuxIso\\CentOS-6.8-x86_64-bin-DVD1.iso");

        File file1 = new File("F:\\linux\\linuxIso\\ubuntu-18.04.2-desktop-amd64.iso");

        Set<File> files = new HashSet<>();
        files.add(file);
        files.add(file1);
        Client1 client1 = new Client1();
        client1.upload(files, "192.168.1.55", 25420);


        //Thread.currentThread().sleep(1000*5);


    }

    /**
     * �ͻ���1���ļ���ȡ�ϴ�
     *
     * @param files �ļ�
     */
    public void upload(Set<File> files, String ip, int port) {
        Socket socket = null;

        try {

            //��ͷ��Э��ͷ
            socket = new Socket(ip, port);// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
            new ClientThread(socket, files).start();
            System.out.println(socket.hashCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}