/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Server
 * Author:   houjing
 * Date:     2019/12/13 17:20
 * Description: Server
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package caculator.server;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Server〉
 *
 * @author houjing
 * @create 2019/12/13
 * @since 1.0.0
 */
public class Server {
    private static int DEFAULT_PORT = 8888;
    private static AsyncServerHandler serverHandle;
    public volatile static long clientCount = 0;
    public static void start(){
        start(DEFAULT_PORT);
    }
    public static synchronized void start(int port){
        if(serverHandle!=null)
            return;
        serverHandle = new AsyncServerHandler(port);
        new Thread(serverHandle,"Server").start();
    }
    public static void main(String[] args){
        Server.start();
    }
}