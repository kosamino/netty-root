package bat.ke.qq.com.consumer;

import bat.ke.qq.com.client.Param;
import bat.ke.qq.com.client.QueryStudentClient;
import bat.ke.qq.com.client.Result;
import bat.ke.qq.com.client.StudentBean;
import bat.ke.qq.com.kernel.remote.RpcProxyFactory;
import bat.ke.qq.com.provider.QueryStudentClientImpl;

import java.util.Scanner;

/**
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class ConsumerService {

    public void queryStudent(String name) {
        RpcProxyFactory rpcProxyFactory = new RpcProxyFactory();

        QueryStudentClient client = (QueryStudentClient)rpcProxyFactory.factoryRemoteInvoker("localhost",8080,QueryStudentClient.class);//todo 代理对象

        Param param = new Param();
        param.setName(name);

        Result<StudentBean> result = client.query(param);
        if (result != null && result.isSuccess()) {
            StudentBean outBean = result.getData();
            System.out.println("姓名:" + outBean.getName());
            System.out.println("性别:" + outBean.getSex());
            System.out.println("年龄:" + outBean.getAge());
            System.out.println("尺寸:" + outBean.getSize());
        } else {
            System.out.println(param.getName() + "查无此人");
        }
    }

    public static void main(String[] args) {
        ConsumerService service = new ConsumerService();

        Scanner sc = new Scanner(System.in);
        //利用hasNextXXX()判断是否还有下一输入项
        System.out.println("输入要查询的老师姓名");
        while (sc.hasNext()) {
            //利用nextXXX()方法输出内容
            String str = sc.next();
            service.queryStudent(str);
        }
    }
}
