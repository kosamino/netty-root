package bat.ke.qq.com.provider;

import bat.ke.qq.com.client.Param;
import bat.ke.qq.com.client.QueryStudentClient;
import bat.ke.qq.com.client.Result;
import bat.ke.qq.com.client.StudentBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class QueryStudentClientImpl implements QueryStudentClient {

    private static Map<String, StudentBean> studentMap =new HashMap<>();

    @Override
    public Result<StudentBean> query(Param param) {
        Result<StudentBean> result=new Result<>();

        StudentBean bean= studentMap.get(param.getName());
        if(bean!=null){
            result.setSuccess(true);
            result.setData(bean);
        }else {
            result.setSuccess(false);
            result.setFailCode("500");
        }
        return result;
    }

    static {
        StudentBean t1=new StudentBean();
        t1.setName("fox");
        t1.setAge(66);
        t1.setSex("man");
        t1.setSize(175);

        StudentBean t2=new StudentBean();
        t2.setName("monkey");
        t2.setAge(66);
        t2.setSex("man");
        t2.setSize(170);

        studentMap.put("fox",t1);
        studentMap.put("monkey",t2);
    }

}
