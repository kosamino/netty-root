package bat.ke.qq.com.client;

import java.io.Serializable;

/**
 * 请求参数
 * 源码学院-ANT
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
public class Param implements Serializable {


    private static final long serialVersionUID = -4704852030666387498L;

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
