package bat.ke.qq.com.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class AcceptCompletionHandler implements CompletionHandler {

    @Override
    public void completed(Object result, Object attachment) {
        ((AioServerHandler)attachment).asynchronousServerSocketChannel.accept(attachment,this);
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        //发起读请求，有数据读到，系统调用ReadCompletionHandler 句柄
        ((AsynchronousSocketChannel)result).read(buffer,buffer,new ReadCompletionHandler((AsynchronousSocketChannel)result));
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
        ((AioServerHandler)attachment).latch.countDown();
    }
}
