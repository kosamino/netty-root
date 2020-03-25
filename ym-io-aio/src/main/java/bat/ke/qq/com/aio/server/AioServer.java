package bat.ke.qq.com.aio.server;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class AioServer {
    public static void main(String[] args) {
        new Thread(new AioServerHandler(8080), "aio-server-001").start();
    }
}
