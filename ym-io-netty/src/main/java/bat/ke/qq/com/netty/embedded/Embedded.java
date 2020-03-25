package bat.ke.qq.com.netty.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;

public class Embedded {
    public static void main(String[] args) {
        ByteBuf byteBuf= Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);
        EmbeddedChannel embeddedChannel=new EmbeddedChannel(new NettyClientHandler());
        embeddedChannel.writeInbound(byteBuf);
    }
}
