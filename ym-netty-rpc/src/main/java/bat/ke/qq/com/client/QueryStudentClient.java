package bat.ke.qq.com.client;

/**
 * 查询接口定义
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public interface QueryStudentClient {
    public Result<StudentBean> query(Param param);
}
