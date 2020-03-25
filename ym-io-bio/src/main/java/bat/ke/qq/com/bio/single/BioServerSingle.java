package bat.ke.qq.com.bio.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class BioServerSingle {  //blocking
    public static void main(String[] args) {
        // 服务端开启一个端口进行监听
        int port = 8080;
        ServerSocket serverSocket = null;   //服务端
        Socket socket;  //客户端
        InputStream in = null;
        OutputStream out = null;
        try {

            serverSocket = new ServerSocket(port);  //通过构造函数创建ServerSocket，指定监听端口，如果端口合法且空闲，服务器就会监听成功
            // 通过无限循环监听客户端连接，如果没有客户端接入，则会阻塞在accept操作
            while (true) {
                System.out.println("Waiting for a new Socket to establish" + " ," + new Date().toString());
                socket = serverSocket.accept();//阻塞  三次握手
                in = socket.getInputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) > 0) {//阻塞
                    System.out.println("input is:" + new String(buffer, 0, length) + " ," + new Date().toString());
                    out = socket.getOutputStream();
                    out.write("success".getBytes());
                    System.out.println("Server end" + " ," + new Date().toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 必要的清理活动
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
