/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Caculator
 * Author:   houjing
 * Date:     2019/12/13 20:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package caculator.client;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author houjing
 * @create 2019/12/13
 * @since 1.0.0
 */
public final class Caculator {
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    public static Object cal(String expression) throws ScriptException {
        return jse.eval(expression);
    }
}