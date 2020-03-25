package bat.ke.qq.com.nio.client;

public class NioClient {
    public static void main(String[] args) {

        new Thread(new NioClientHandler("localhost", 8080), "nioClient-001").start();

    }
}