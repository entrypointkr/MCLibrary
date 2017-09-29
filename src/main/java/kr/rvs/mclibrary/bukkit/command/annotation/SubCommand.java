package kr.rvs.mclibrary.bukkit.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    Class<?>[] value() default {};
}
