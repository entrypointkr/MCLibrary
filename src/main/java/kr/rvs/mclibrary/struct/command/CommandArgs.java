package kr.rvs.mclibrary.struct.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandArgs {
    CommandType type() default CommandType.DEFAULT;

    String usage() default "";

    String desc() default "";

    String args() default "";

    int min() default -1;

    int max() default -1;
}
