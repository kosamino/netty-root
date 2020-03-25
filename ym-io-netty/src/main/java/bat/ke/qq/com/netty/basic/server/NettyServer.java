package bat.ke.qq.com.netty.basic.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * (1)、 初始化用于Acceptor的主"线程池"以及用于I/O工作的从"线程池"；
 * (2)、 初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口；
 * (3)、 通过ServerBootstrap的group方法，设置（1）中初始化的主从"线程池"；
 * (4)、 指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
 * (5)、 设置ServerSocketChannel的处理器
 * (6)、 设置子通道也就是SocketChannel的处理器， 其内部是实际业务开发的"主战场"
 * (8)、 配置子通道也就是SocketChannel的选项
 * (9)、 绑定并侦听某个端口
 */

public class NettyServer {
    public void bind(int port) throws Exception {

        // 定义一对线程组
        // 主线程组, 用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组, 老板线程组会把任务丢给他，让手下线程组去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty服务器的创建, 辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)           //绑定两个线程组
                    .channel(NioServerSocketChannel.class)   //指定NIO的模式
                    /**
                     * @Description: 初始化器，channel注册后，会执行里面的相应的初始化方法
                     *
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        // 通过SocketChannel去获得对应的管道
                        ChannelPipeline pipeline = channel.pipeline();

                        // 通过管道，添加handler
                        pipeline.addLast("nettyServerOutBoundHandler", new NettyServerOutBoundHandler());
                        pipeline.addLast("nettyServerHandler", new NettyServerHandler());
                    }
                     * 子处理器也可以通过下面的内部方法来实现。
                    */
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 子处理器，用于处理workerGroup
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerOutBoundHandler());
                            socketChannel.pipeline().addLast(new NettyServerHandler());

                        }
                    });

            // 启动server，并且设置8088为启动的端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            System.out.println("server start");
            // 监听关闭的channel，设置位同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            //退出线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyServer().bind(port);
    }
}
