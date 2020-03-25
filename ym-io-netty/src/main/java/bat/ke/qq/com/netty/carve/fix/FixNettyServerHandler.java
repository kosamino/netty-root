package bat.ke.qq.com.netty.carve.fix;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
//@ChannelHandler.Sharable/*该handler能在多个线程间共享，那么实现必须是线程安全的*/
public class FixNettyServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger readCount =new AtomicInteger(0);
    private AtomicInteger completeCout =new AtomicInteger(0);

    public FixNettyServerHandler(){

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)
    throws  Exception{
        ByteBuf byteBuf= (ByteBuf)msg;
        byteBuf.touch();
        ReferenceCountUtil.touch(msg);
        byte[] bytes=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("Server Accept:["+new String(bytes,CharsetUtil.UTF_8)+"]"+
                readCount.incrementAndGet());

        ByteBuf response= Unpooled.copiedBuffer(("hello netty client").getBytes());
        ctx.writeAndFlush(response);
//        ctx.fireChannelRead(response);
    }

    /**
     * 把内核缓冲区的数据都读完了才执行
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        System.out.println("inHandler channelReadComplete:"+ completeCout.incrementAndGet());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("NettyServerHandler channelRegistered");
        super.channelRegistered(ctx);
        //
//        ctx.pipeline().addLast(1);
//        ctx.pipeline().addLast(2);
//        ctx.pipeline().addLast(3);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("NettyServerHandler channelActive");
        super.channelActive(ctx);
    }
}
