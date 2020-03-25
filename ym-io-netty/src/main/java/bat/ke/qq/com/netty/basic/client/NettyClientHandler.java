package bat.ke.qq.com.netty.basic.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.logging.Logger;


/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger logger = Logger.getLogger(NettyClientHandler.class.getName());

    /**
     * 通道被激活触发的操作（链接成功后,写入数据）
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 500; i++) {
            // netty4  池化概念  节约了和系统之间的交互(内存分配和销毁)
            // 非池化
            // ByteBuf buf = ctx.alloc().directBuffer();
            ByteBuf outBuffer = Unpooled.copiedBuffer("hello netty server"+i, CharsetUtil.UTF_8);
            // ctx.write(outBuffer);
            // ctx.flush();
            ctx.writeAndFlush(outBuffer);
        }
    }

    /**
     * 读到数据事触发的方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        System.out.println("accept msg:" + msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
