package bat.ke.qq.com.netty.push.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public class KeepChannelHandlerContexts {
    public static ChannelHandlerContext context=null;

    public static ChannelHandlerContext getContext() {
        return context;
    }

    public static void setContext(ChannelHandlerContext context) {
        KeepChannelHandlerContexts.context = context;
    }
}
