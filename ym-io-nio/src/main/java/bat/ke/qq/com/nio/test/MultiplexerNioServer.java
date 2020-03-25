package bat.ke.qq.com.nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerNioServer implements Runnable{

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private volatile boolean stop = false;

    /**
     * 初始化多路复用器 绑定监听端口
     * @param port
     */
    public MultiplexerNioServer(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();//获得一个serverChannel
            selector = Selector.open();//获得一个多路复用器
            serverSocketChannel.configureBlocking(false);//设置为非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);//绑定一个端口和等待队列长度
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//把selector注册到channel，关注链接事件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                int client = selector.select(1000);
                System.out.println("start");
                if (client == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey selectionKey =null;
                while (it.hasNext()){
                    selectionKey = it.next();
                    it.remove();
                    try {
                        //处理事件
                        handle(selectionKey);
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if (selectionKey != null) {
                            if (selectionKey.channel() != null){
                                selectionKey.channel().close();
                            }

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (selector != null) {
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }
        }

    }


    public void handle(SelectionKey key) throws IOException {
        if (key.isValid()){
            //连接事件
            if (key.isAcceptable()){
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();//三次握手
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);//连接建立后关注读事件
                    System.out.println("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (key.isReadable()){
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(readBuffer);
                if (read >0){
                    readBuffer.flip();//读写模式反转
                    byte[] bytes =new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("input is:" + body);

                    res(socketChannel, body);
                }
            }
        }
    }

    private void res(SocketChannel channel, String response) throws IOException {
        if (response != null && response.length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
            System.out.println("res end");
        }
    }
}
