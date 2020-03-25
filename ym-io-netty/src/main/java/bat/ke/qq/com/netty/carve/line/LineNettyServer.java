package bat.ke.qq.com.netty.carve.line;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class LineNettyServer {
    public void bind(int port)throws Exception{
        LineNettyServerHandler serverHandler= new LineNettyServerHandler();

        EventLoopGroup  bossGroup=new NioEventLoopGroup();//selector[]
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();//服务端引导程序
            b.group(bossGroup ,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new LineNettyServerHandler());
                        }
                    });
            ChannelFuture f=b.bind(port).sync();
            System.out.println("server start");
            f.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        int port=8080;
        new LineNettyServer().bind(port);
    }
}
