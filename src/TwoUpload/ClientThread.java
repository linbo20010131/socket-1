package TwoUpload;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientThread extends Thread {


    DataInputStream dataInput = null;
    DataOutputStream dataOutput = null;
    String uploaderName = "林波";
    private Socket socket = null;
    private Set<File> files = null;

    public ClientThread(Socket socket, Set<File> files) {
        this.socket = socket;
        this.files = files;
    }

    @Override
    public void run() {

        try {

            for (File file : files
            ) {
                //包头，协议头
                dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// 由Socket对象得到输入流,并构造相应的DataOutputStream对象


                String fileName = file.getName();// 得到文件名
                dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length()); // 将文件名的长度转码之后(解决文件名是中文出现乱码的问题)写过去
                dataOutput.write(fileName.getBytes());// 将文件名写过去
                dataOutput.flush();

                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// 得到文件类型
                dataOutput.writeInt(fileType.length());// 将文件类型的长度写过去
                dataOutput.write(fileType.getBytes());// 将文件类型写过去
                dataOutput.flush();

                dataOutput.writeLong(file.length()); // 文件内容长度写过去
                dataOutput.flush();

                dataOutput.writeInt(new String(uploaderName.getBytes(), "ISO-8859-1").length());// 将上传者的长度写过去
                dataOutput.write(uploaderName.getBytes());// 将上传者写过去
                dataOutput.flush();

                System.out.print("[文件名:" + fileName + " | ");
                System.out.print("文件名长度:" + file.getName().length() + " | ");
                System.out.print("文件类型:" + fileType + " | ");
                System.out.print("文件内容长度:" + file.length() + " | ");
                System.out.print("上传者:" + uploaderName + "]" + "\n");

                byte[] byt = new byte[1024 * 1024 * 20];// new一个byte数组
                int len = 0;
                while ((len = dataInput.read(byt)) > 0) {// 只要文件内容的长度大于0,就一直读取

                    dataOutput.write(byt, 0, len);// 将读取的文件内容写过去
                    dataOutput.flush();
                }
                System.out.println("读取成功");// 读取成功
            }

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
