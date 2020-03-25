package bat.ke.qq.com.netty.basic.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */

public class NettyClient {
    private ChannelFuture channelFuture ;

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            Bootstrap bootstrap = new Bootstrap();//客户端启动程序
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            channelFuture = bootstrap.connect(host, port).sync();/*连接到远程节点，阻塞等待直到连接完成*/
            channelFuture.channel().closeFuture().sync();/*阻塞，直到channel关闭*/
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8088;
        NettyClient client = new NettyClient();
        client.connect(port, "localhost");
        ChannelFuture cf = client.getChannelFuture();
    }


    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }
}
