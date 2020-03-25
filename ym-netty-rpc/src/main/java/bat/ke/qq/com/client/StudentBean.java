package bat.ke.qq.com.client;

import java.io.Serializable;

/**
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class StudentBean implements Serializable {

    private static final long serialVersionUID = -605498797347398869L;

    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 尺寸
     */
    private int size;

    public StudentBean() {

    }

    public StudentBean(String name, int age, String sex, int size) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
