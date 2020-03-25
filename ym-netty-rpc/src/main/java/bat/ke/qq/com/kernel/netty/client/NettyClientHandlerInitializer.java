package bat.ke.qq.com.kernel.netty.client;


import bat.ke.qq.com.kernel.netty.hessian.HessianDecode;
import bat.ke.qq.com.kernel.netty.hessian.HessianEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.CountDownLatch;

public class NettyClientHandlerInitializer extends ChannelInitializer<SocketChannel>{
    private CountDownLatch latch;
    private NettyClientHandler handler;

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        handler =  new NettyClientHandler(latch);

        HessianEncode hessionEncodeHandler=new HessianEncode();
        HessianDecode hessionDecodeHandler= new HessianDecode();

        LengthFieldPrepender fieldEncoder=new LengthFieldPrepender(2);
//        LengthFieldBasedFrameDecoder fieldDecoder = new LengthFieldBasedFrameDecoder(65535,
//                0, 2, 0, 2);

        //出站
        sc.pipeline().addLast(fieldEncoder);
        sc.pipeline().addLast(hessionEncodeHandler);

        //入站        LengthFieldBasedFrameDecoder多线程下不安全，因此使用new
        sc.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                0, 2, 0, 2));
        sc.pipeline().addLast(hessionDecodeHandler);
        sc.pipeline().addLast(handler);
    }
    public Object getServerResult(){
        return handler.getResult();
    }

    public NettyClientHandlerInitializer(CountDownLatch latch) {
        this.latch = latch;
    }

    public void reLatch(CountDownLatch latch){
        this.handler.reLatch(latch);
    }
}