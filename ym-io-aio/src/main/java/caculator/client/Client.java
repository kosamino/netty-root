/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Client
 * Author:   houjing
 * Date:     2019/12/13 17:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package caculator.client;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author houjing
 * @create 2019/12/13
 * @since 1.0.0
 */
public class Client {
    private static String DEFAULT_HOST = "localhost";
    private static int DEFAULT_PORT = 8888;
    private static AsyncClientHandler clientHandle;
    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }
    public static synchronized void start(String ip,int port){
        if(clientHandle!=null)
            return;
        clientHandle = new AsyncClientHandler(ip,port);
        new Thread(clientHandle,"Client").start();
    }
    //向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception{
        if(msg.equals("q")) return false;
        clientHandle.sendMsg(msg);
        return true;
    }
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
    }
}