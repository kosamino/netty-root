package bat.ke.qq.com.kernel.netty.client;

import bat.ke.qq.com.kernel.remote.RpcContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.CountDownLatch;

/**
 *  Netty客户端
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class NettyClient {

    private String host;
    private int port;

    private Bootstrap b;
    private EventLoopGroup group;
    private ChannelFuture cf;
    private NettyClientHandlerInitializer clientInitializer;
    private CountDownLatch latch;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        latch=new CountDownLatch(0);
        clientInitializer = new NettyClientHandlerInitializer(latch);
        group = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(clientInitializer);
    }

    public void connect() {
        try {
            this.cf = b.connect(host, port).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public ChannelFuture getChannelFuture() {
        if (this.cf == null) {
            this.connect();
        }
        if (!this.cf.channel().isActive()) {
            this.connect();
        }
        return this.cf;
    }

    public void close() {
        try {
            this.cf.channel().closeFuture().sync();
            this.group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端发送数据方法
     * @param rpcRequest
     * @return
     * @throws InterruptedException
     */
    public Object sendData(RpcContext rpcRequest) throws InterruptedException {
        ChannelFuture cf = this.getChannelFuture();//单例模式获取ChannelFuture对象
        if (cf.channel() != null && cf.channel().isActive()) {
            latch=new CountDownLatch(1);
            clientInitializer.reLatch(latch);
            cf.channel().writeAndFlush(rpcRequest);
            latch.await();
        }

        return clientInitializer.getServerResult();
    }
}
