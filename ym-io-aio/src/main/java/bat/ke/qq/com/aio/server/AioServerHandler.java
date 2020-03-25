package bat.ke.qq.com.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class AioServerHandler implements Runnable{

    CountDownLatch latch;//保持server进程的运行
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AioServerHandler(int port){

        try {
            asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Aio server start'");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        latch=new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void doAccept(){
//        attachment为我们当前句柄 AioServerHandler
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
