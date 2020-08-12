package OneClientManyFile;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static  String basePath = "F:\\Program Files";


    public static void main(String[] args) throws Exception {

        socket = new Socket("192.168.1.55", 25420);
        //获取socket输出流
        dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        SendFile(basePath);
        System.out.println("发送完成");
        while (true){

        }



    }


    public static void SendFile(String FileHeadPath) throws Exception {

        //根据基础路径获取文件或文件夹
        String files[] = new File(FileHeadPath).list();
        for (String filePath : files) {
            //文件
            File file = new File(FileHeadPath + File.separator + filePath);
            //输入流
            //判断路径是文件还是文件夹 1 文件夹  2 文件
            if (file.isDirectory()) {
                dataOutput.writeInt(1);
                dataOutput.flush();

                //获取文件夹的路径
                String path = file.getPath().replace(basePath, "");
                System.out.println(path);

                //发送文件夹路径长度
                dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
                //发送文件夹路径名字
                dataOutput.write(path.getBytes());
                dataOutput.flush();

                //递归继续发送

               // String files1[] = file.list();
              //  for (String files1Path:files1
                 //    ) {
                    SendFile(FileHeadPath + File.separator + filePath);
              //  }



            }
            if (file.isFile()) {
                dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                //发送mark表示符
                dataOutput.writeInt(2);
                dataOutput.flush();

                //发送文件路径
                String fileName = file.getPath().replace(basePath,"");
                dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length()); // 将文件名的长度转码之后(解决文件名是中文出现乱码的问题)写过去
                dataOutput.write(fileName.getBytes());// 将文件名写过去
                dataOutput.flush();


                //发送文件类型
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// 得到文件类型
                dataOutput.writeInt(fileType.length());// 将文件类型的长度写过去
                dataOutput.write(fileType.getBytes());// 将文件类型写过去
                dataOutput.flush();


                //文件长度
                dataOutput.writeLong(file.length()); // 文件内容长度写过去
                dataOutput.flush();

                byte[] byt = new byte[1024 * 1024 * 20];// new一个byte数组
                int len = 0;
                while ((len = dataInput.read(byt)) > 0) {// 只要文件内容的长度大于0,就一直读取
                    dataOutput.write(byt,0, len);// 将读取的文件内容写过去
                    dataOutput.flush();
                }
                System.out.println("文件：" + fileName + "读取成功");// 读取成功
                dataInput.close();


            }

        }



    }

}
