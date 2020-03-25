package bat.ke.qq.com.netty.serialize.handler;

import bat.ke.qq.com.netty.serialize.utils.HessianSerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 解码器
 *
 * @author Administrator
 */
@ChannelHandler.Sharable
public class HessianDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
                          List<Object> out) throws Exception {
        final byte[] array;
        final int length = msg.readableBytes();
        array = new byte[length];
        msg.getBytes(msg.readerIndex(), array, 0, length);
        out.add(HessianSerializerUtil.deserialize(array));
    }
}