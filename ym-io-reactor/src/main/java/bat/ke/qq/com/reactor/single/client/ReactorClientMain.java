package bat.ke.qq.com.reactor.single.client;

public class ReactorClientMain {
    public static void main(String[] args)throws Exception {
//        new Thread(new ReactorClient("localhost",8080),"reactorClient-001").start();
        new Thread(new ReactorClient("localhost",8080),"reactorClient-002").start();
    }
}
