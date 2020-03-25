package bat.ke.qq.com.bio.thred;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class BioServerThread {
    public static void main(String[] args) {
        int port=8080;
        ServerSocket serverSocket=null;
        try{

            serverSocket=new ServerSocket(port);
            Socket socket=null;
            while (true){
                socket=serverSocket.accept();//拿到socket
                //连接量大的时候 会拖垮cpu
                new Thread(new SocketHandler(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                    serverSocket=null;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
