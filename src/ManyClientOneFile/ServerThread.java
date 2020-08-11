package ManyClientOneFile;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private String basePath;
    private Socket socket;

    public ServerThread(String basePath, Socket socket) {
        this.basePath = basePath;
        this.socket = socket;
    }

    @Override
    public void run() {

        DataInputStream dataInput = null;
        DataOutputStream dataOutput = null;
        try {
            dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int mark = 0;
            //文件存储目录
            String filePath = basePath;
            //f:\\
            //获取传过来的mark值
            while (true) {
                if (dataInput.available() >= 4) {
                    mark = dataInput.readInt(); //
                    break;
                }
            }
            //如果mark 1 文件夹 2 文件
            if (mark == 1) {
                //获取文件路径长度
                int filePathLength = 0;
                while (true) {
                    if (dataInput.available() >= 4) {
                        filePathLength = dataInput.readInt(); // 得到客户端写过来的文件长度,　　　4个字节
                        break;
                    }
                }
                //获取文件路径名字
                byte[] bytes = new byte[filePathLength];
                int pathLength = 0;
                String pathName = null;
                while (true) {// 直到将文件名的长度全部读取完成
                    pathLength += dataInput.read(bytes);
                    if (filePathLength == pathLength) {
                        break;
                    }
                }
                pathName = new String(bytes);
                //文件夹存储的路径
                filePath += pathName;
                System.out.println(filePath);

                File file = new File(filePath);
                //如果文件夹不存在，就创建
                if (!file.exists()) {
                    file.mkdirs();
                }

                //下载文件
            } else if (mark == 2) {
                //获取文件名长度
                int fileNameLength = 0;
                while (true) {
                    if (dataInput.available() >= 4) {
                        fileNameLength = dataInput.readInt();
                        break;
                    }
                }
                //获取文件名
                byte[] bytes = new byte[fileNameLength];
                int nameLength = 0;
                String fileName = null;
                while (true) {
                    nameLength += dataInput.read(bytes);
                    if (nameLength == fileNameLength) {
                        break;
                    }
                }
                fileName = new String(bytes);

                //获取文件类型长度
                int fileTypeLength = 0;
                while (true) {
                    if (dataInput.available() >= 4) {
                        fileTypeLength = dataInput.readInt();
                        break;
                    }
                }
                //获取文件类型
                byte[] bytes1 = new byte[fileTypeLength];
                int fileTypeNameLength = 0;
                String fileType = null;
                while (true) {
                    fileTypeNameLength += dataInput.read(bytes1);
                    if (fileTypeNameLength == fileTypeLength) {
                        break;
                    }
                }
                fileType = new String(bytes1);

                //获取文件长度
                long longLength = dataInput.readLong();

                //文件存储位置
                filePath += fileName;

                System.out.print("[文件名:" + fileName + " | ");
                System.out.print("文件名长度:" + fileNameLength + " | ");
                System.out.print("文件类型:" + fileType + " | ");
                System.out.print("文件内容长度:" + longLength + " | ");
                System.out.println("存放路径:" + filePath + "]");
                File file = new File(filePath);
                //如果文件不存在就创建

                if (!file.exists()) {
                    File file1 = new File(file.getParent());
                    //如果文件的父目录不存在，就创建
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    file.createNewFile();
                }
                dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));

                byte byt[] = new byte[1024 * 1024 * 20];// 每次读取20M
                //累加长度
                long sumLength = 0;
                int len = 0;
                while (true) {
                    if (longLength == sumLength) {
                        break;
                    }
                    //如果总长度-累加长度 大于可读长度
                    //1000 920           80        100
                    if (longLength - sumLength < dataInput.available()) {
                        //读取文件剩余的字节
                        len = dataInput.read(byt, 0, Integer.valueOf(String.valueOf(longLength - sumLength)));
                    } else {
                        //剩余长度小于 可读
                        if (longLength - sumLength > (1024 * 1024 * 20)) {
                            len = dataInput.read(byt);
                        } else {
                            len = dataInput.read(byt, 0, Integer.valueOf(String.valueOf(longLength - sumLength)));
                        }
                    }
                    sumLength += len;
                    dataOutput.write(byt, 0, len);
                    dataOutput.flush();
                }
                System.out.println("写入成功");
                //关闭dataOutput
                dataOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataInput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
