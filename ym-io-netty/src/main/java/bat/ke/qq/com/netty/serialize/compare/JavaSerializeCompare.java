package bat.ke.qq.com.netty.serialize.compare;

import bat.ke.qq.com.netty.serialize.StudentInfo;
import bat.ke.qq.com.netty.serialize.utils.HessianSerializerUtil;
import bat.ke.qq.com.netty.serialize.utils.KryoSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static java.awt.SystemColor.info;

public class JavaSerializeCompare {
    public static void main(String[] args)throws IOException {
        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setUserName("Kosamino");
        studentInfo.setAge(25);
        studentInfo.setSex("男");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        //大小    消耗时间

        byte[] k=KryoSerializer.serialize(studentInfo);
        System.out.println("The kryo serializable length is : "+k.length);

        byte[] h= HessianSerializerUtil.serialize(studentInfo);
        System.out.println("The Hessian serializable length is : "+h.length);


    }
}
