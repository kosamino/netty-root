package bat.ke.qq.com;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtils {
    public static List<String> res=new ArrayList<>();
    public static Integer count=0;
    static {
        res.add("怒发冲冠，凭阑处,潇潇雨歇。");
        res.add("抬望眼，仰天长啸，壮怀激烈。");
        res.add("三十功名尘与土，八千里路云和月");
        res.add("莫等闲，白了少年头，空悲切。");
        res.add("靖康耻，犹未雪；臣子恨，何时灭？");
        res.add("驾长车，踏破贺兰山缺。");
        res.add("壮志饥餐胡虏肉，笑谈渴饮匈奴血。");
        res.add("待从头，收拾旧山河，朝天阙。");
    }

    public static String getResult(){
        String result=res.get(count);
        if(count++>=res.size()-1){
            count=0;
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(ResponseUtils.getResult());
        }
    }
}
