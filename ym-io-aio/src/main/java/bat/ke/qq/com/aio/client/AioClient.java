package bat.ke.qq.com.aio.client;


/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class AioClient {
    public static void main(String[] args) {
        AioClientHandler aioClientHandler=new AioClientHandler("localhost", 8083);
        new Thread(aioClientHandler).start();
    }
}
