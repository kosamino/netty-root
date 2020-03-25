package bat.ke.qq.com.bio.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTest {
    public static void main(String[] args) {
        final Object lock=new Object();
        Thread bioServer =new  Thread(){
            public void run() {
                synchronized (lock){
                    int port = 8080;
                    ServerSocket serverSocket = null;//服务端
                    Socket socket = null;//客户端
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        serverSocket = new ServerSocket(port);//指定端口
                        while (true) {
                            System.out.println("start");
                            socket = serverSocket.accept();//阻塞
                            in = socket.getInputStream();
                            byte[] buffer = new byte[1024];
                            int length = 0;
                            while ((length = in.read(buffer)) > 0) {//阻塞
                                System.out.println("input is:" + new String(buffer, 0, length));
                                out = socket.getOutputStream();
                                out.write("success".getBytes());
                                System.out.println("end");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (serverSocket != null) {
                            try {
                                serverSocket.close();
                                serverSocket = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (in != null) {
                            try {
                                in.close();
                                in = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                                out = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        };

        bioServer.setName("bioServer");
        bioServer.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            System.out.println("end");
        }
    }
}
