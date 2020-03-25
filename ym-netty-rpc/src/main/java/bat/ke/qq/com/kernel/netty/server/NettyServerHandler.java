package bat.ke.qq.com.kernel.netty.server;

import bat.ke.qq.com.kernel.registry.Registry;
import bat.ke.qq.com.kernel.remote.RpcContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
@ChannelHandler.Sharable/*该handler能在多个线程间共享，那么实现必须是线程安全的*/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcContext model=(RpcContext)msg;

        Class clazz=null;
        if(Registry.map.containsKey(model.getClassName())){
            clazz=Registry.map.get(model.getClassName());
        }

        Object result=null;
        try {
            Method method=clazz.getMethod(model.getMethodName(),model.getTypes());
            result=method.invoke(clazz.newInstance(),model.getParams());
        }catch (Exception e){
            e.printStackTrace();
        }

        ctx.channel().writeAndFlush(result);
    }

}