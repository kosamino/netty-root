package bat.ke.qq.com.netty.carve.line;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
//@ChannelHandler.Sharable/*该handler能在多个线程间共享，那么实现必须是线程安全的*/
public class LineNettyServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger readCount =new AtomicInteger(0);
    private AtomicInteger completeCout =new AtomicInteger(0);

    public LineNettyServerHandler(){

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)
    throws  Exception{
        ByteBuf byteBuf= (ByteBuf)msg;
        byte[] bytes=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("Server Accept:["+new String(bytes,CharsetUtil.UTF_8)+"]"+
                readCount.incrementAndGet());

        ByteBuf response= Unpooled.copiedBuffer(("hello netty client"+ System.getProperty("line.separator")).getBytes());
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
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
