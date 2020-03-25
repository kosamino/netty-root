package bat.ke.qq.com.kernel.remote;

import bat.ke.qq.com.kernel.netty.client.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 远程处理器
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private String host;
    private int port;
    private Class interfaces;

    public RemoteInvocationHandler(String host, int port, Class interfaces) {
        this.host = host;
        this.port = port;
        this.interfaces = interfaces;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //todo 封装消息
        RpcContext rpcContext=new RpcContext();
        rpcContext.setClassName(interfaces.getName());
        rpcContext.setMethodName(method.getName());
        rpcContext.setTypes(method.getParameterTypes());
        rpcContext.setParams(args);

        try {
            //通讯
            NettyClient client=new NettyClient(host,port);
            client.connect();
            return client.sendData(rpcContext);
        }catch (Exception e){

        }
        return null;
    }
}
