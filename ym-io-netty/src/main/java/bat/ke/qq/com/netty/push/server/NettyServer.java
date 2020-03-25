package bat.ke.qq.com.netty.push.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * 客户端连接后，保持连接，通过第一次有事件发生的时候把对应的ChannelHandlerContext留存，
 * 通过ChannelHandlerContext进行给客户端的消息推送
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class NettyServer {
    public static CountDownLatch latch=new CountDownLatch(1);
    public void bind(int port)throws Exception{
        NettyServerHandler serverHandler= new NettyServerHandler();

        EventLoopGroup  bossGroup=new NioEventLoopGroup();//selector[]
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();//服务端引导程序
            b.group(bossGroup ,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture f=b.bind(port).sync();
            System.out.println("server start");
            latch.countDown();
            f.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        new Thread(){
            @Override
            public void run() {
                int port=8080;
                try {
                    new NettyServer().bind(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        latch.await();

        Scanner sc = new Scanner(System.in);
        //利用hasNextXXX()判断是否还有下一输入项
        System.out.println("输入需要发送的数据");
        while (sc.hasNext()) {
            //利用nextXXX()方法输出内容
            String str = sc.next();
            ChannelHandlerContext ctx =KeepChannelHandlerContexts.getContext();
            if(ctx==null){
                System.out.println("还没有连接");
                continue;
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
        }


    }
}
