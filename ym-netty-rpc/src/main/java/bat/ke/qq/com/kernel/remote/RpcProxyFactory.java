package bat.ke.qq.com.kernel.remote;

import java.lang.reflect.Proxy;

/**
 * 远程代理工厂
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class RpcProxyFactory<T> {

    public T factoryRemoteInvoker(String host, int port, Class interfaces){
        //动态代理
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(),new Class[]{interfaces},
                new RemoteInvocationHandler(host,port,interfaces));
    }
}
