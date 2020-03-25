package bat.ke.qq.com.reactor.single.server;

public class ReactorServer {

    public static void main(String[] args) throws Exception{
        new Thread(new Reactor(8080),"reactor-001").start();
    }
}
