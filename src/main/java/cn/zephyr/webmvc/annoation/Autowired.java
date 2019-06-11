package cn.zephyr.webmvc.annoation;

import java.lang.annotation.*;

/**
 * @Author: zephyrLai
 * @Description: 自动注入
 * @Date: 2019/6/11 11:25
 */
@Target({ElementType.FIELD})   // 作用在属性上
@Retention(RetentionPolicy.RUNTIME) // 运行时起效
@Documented //可被识别
public @interface Autowired {

    String value() default "";
}
