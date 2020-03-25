package bat.ke.qq.com.netty.carve.field;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;


/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */

public class FieldNettyClient {
    private ChannelFuture f;

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            Bootstrap b = new Bootstrap();//客户端启动程序
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast("frameEncoder",
                                    new LengthFieldPrepender(2));

                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                                    0,2,0,
                                    2));
                            socketChannel.pipeline().addLast(new FieldNettyClientHandler());
                        }
                    });

            f = b.connect(host, port).sync();/*连接到远程节点，阻塞等待直到连接完成*/
            f.channel().closeFuture().sync();/*阻塞，直到channel关闭*/
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        FieldNettyClient client = new FieldNettyClient();
        client.connect(port, "localhost");
        ChannelFuture cf = client.getF();

//        if (cf.channel() != null && cf.channel().isActive()) {
//            for (int i = 0; i < 5000 ; i++) {
//                ByteBuf outBuffer = Unpooled.copiedBuffer("hello netty server", CharsetUtil.UTF_8);
//                cf.channel().writeAndFlush(outBuffer);
//
//
//            }
//        }
    }


    public ChannelFuture getF() {
        return f;
    }

    public void setF(ChannelFuture f) {
        this.f = f;
    }
}
