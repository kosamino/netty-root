package bat.ke.qq.com.provider;

import bat.ke.qq.com.client.QueryStudentClient;
import bat.ke.qq.com.kernel.netty.server.NettyServer;
import bat.ke.qq.com.kernel.registry.Registry;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class ProviderMain {
    public static void main(String[] args) {
        //TODO 初始化服务实例容器
        Registry.map.put(QueryStudentClient.class.getName(),QueryStudentClientImpl.class);

        NettyServer server=new NettyServer();
        try {
            server.bind(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
