package bat.ke.qq.com.kernel.netty.server;

import bat.ke.qq.com.kernel.netty.hessian.HessianDecode;
import bat.ke.qq.com.kernel.netty.hessian.HessianEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**

 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class NettyServer {
    public void bind(int port)throws Exception{
        NettyServerHandler serverHandler= new NettyServerHandler();
        HessianEncode hessionEncode=new HessianEncode();
        HessianDecode hessionDecode= new HessianDecode();

        LengthFieldPrepender fieldEncoder=new LengthFieldPrepender(2);
//        LengthFieldBasedFrameDecoder fieldDecoder = new LengthFieldBasedFrameDecoder(65535,
//                0, 2, 0, 2);

        EventLoopGroup  bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();//服务端引导程序
            b.group(bossGroup ,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline =socketChannel.pipeline();
                            //出站
                            pipeline.addLast(fieldEncoder);
                            pipeline.addLast(hessionEncode);

                            //入站
                            //LengthFieldBasedFrameDecoder 不是sharable的，线程不安全因此要new  不同于hessionEncode是线程安全的
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,
                                    0, 2, 0, 2));
                            pipeline.addLast(hessionDecode);
                            pipeline.addLast(serverHandler);
                        }
                    });
            ChannelFuture f=b.bind(port).sync();
            System.out.println("server start port:"+port);
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
