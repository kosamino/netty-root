package bat.ke.qq.com.nio.test;

public class NioServer {
    public static void main(String[] args){
        int port =8080;
        MultiplexerNioServer multiplexerNioServer = new MultiplexerNioServer(port);
        multiplexerNioServer.stop();
        new Thread(multiplexerNioServer,"nioserver-001").start();
    }
}
