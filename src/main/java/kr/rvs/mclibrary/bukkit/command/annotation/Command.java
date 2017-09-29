package kr.rvs.mclibrary.bukkit.command.annotation;

import kr.rvs.mclibrary.bukkit.command.CommandType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    CommandType type() default CommandType.DEFAULT;

    String args();

    String usage() default "";

    String desc() default "";

    String perm() default "";

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
