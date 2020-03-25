package bat.ke.qq.com.kernel.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务实例容器
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class Registry {
    public static ConcurrentHashMap<String, Class> map = new ConcurrentHashMap<String, Class>();
}
