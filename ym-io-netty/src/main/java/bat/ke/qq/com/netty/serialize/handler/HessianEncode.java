package bat.ke.qq.com.netty.serialize.handler;

import bat.ke.qq.com.netty.serialize.utils.HessianSerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author Administrator
 *
 */
@ChannelHandler.Sharable
public class HessianEncode extends MessageToByteEncoder<Object>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf)
            throws Exception {
        byte[] write = HessianSerializerUtil.serialize(msg);
        buf.writeBytes(write);
    }
}