package bat.ke.qq.com.bio.thredpool;

import bat.ke.qq.com.bio.thred.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class BioServerThreadPool {
    public static void main(String[] args) {
        int port=8080;
        ServerSocket serverSocket=null;
        try{
            //1000000
            serverSocket=new ServerSocket(port);
            Socket socket=null;
            //100000    100000
            ServerHandlerExcutePool excutePool=new ServerHandlerExcutePool(2,100);
            while (true){
                socket=serverSocket.accept();
                excutePool.execute(new SocketHandler(socket));
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
