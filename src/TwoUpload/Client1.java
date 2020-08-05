package TwoUpload;

import java.io.File;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文件上传客户端
 *
 * @author liuCong
 * @data 2017年8月23日
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
     * 客户端1的文件读取上传
     *
     * @param files 文件
     */
    public void upload(Set<File> files, String ip, int port) {
        Socket socket = null;

        try {

            //包头，协议头
            socket = new Socket(ip, port);// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
            new ClientThread(socket, files).start();
            System.out.println(socket.hashCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}