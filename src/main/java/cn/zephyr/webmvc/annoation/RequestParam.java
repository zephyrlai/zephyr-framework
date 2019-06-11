package cn.zephyr.webmvc.annoation;

import java.lang.annotation.*;

/**
 * @Author: zephyrLai
 * @Description: TODO
 * @Date: 2019/6/11 11:25
 */
@Target({ElementType.PARAMETER})   // 作用在参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时起效
@Documented //可被识别
public @interface RequestParam {

    String value() default "";
}
