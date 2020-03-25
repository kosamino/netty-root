package bat.ke.qq.com.kernel.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CountDownLatch;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private CountDownLatch latch;
    private Object result;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        result=msg;
        System.out.println("返回数据读取完毕");
        latch.countDown();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public Object getResult() {
        return result;
    }

    public NettyClientHandler(CountDownLatch latch) {
        this.latch = latch;
    }

    public void reLatch(CountDownLatch latch){
        this.latch=latch;
    }
}
