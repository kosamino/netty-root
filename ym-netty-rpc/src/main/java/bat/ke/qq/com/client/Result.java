package bat.ke.qq.com.client;

import java.io.Serializable;

/**
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 3086633178104602825L;

    /**
     * 返回数据对象
     */
    private T data;
    /**
     * 当前请求业务是否成功
     */
    private boolean success;
    /**
     * 失败时的错误code
     */
    private String failCode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }
}
