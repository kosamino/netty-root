package bat.ke.qq.com.netty.basic.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class NettyServerOutBoundHandler extends ChannelOutboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("out one write");
        // 向客户端发送消息
        String response = "hello client!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {

        super.read(ctx);
//        ctx.channel().writeAndFlush(buffer);
        /*
        通常的outbindhandler实现read方法用于拦截协议校验或者安全校验等功能，
       通过拦截调用super.read，否则直接给客户端返回数据，
        */
    }
}
