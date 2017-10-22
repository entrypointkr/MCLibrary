package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.annotation.Command;

import java.lang.annotation.Annotation;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class CommandAnnotationWrapper implements Command {
    private final Command annotation;

    public CommandAnnotationWrapper(Command annotation) {
        this.annotation = annotation;
    }

    public String[] slicedArgs() {
        return annotation.args().split(" ");
    }

    public String firstArg() {
        String[] args = slicedArgs();
        return args.length > 0 ? args[0] : null;
    }

    @Override
    public CommandType type() {
        return annotation.type();
    }

    @Override
    public String args() {
        return annotation.args();
    }

    @Override
    public String usage() {
        return annotation.usage();
    }

    @Override
    public String desc() {
        return annotation.desc();
    }

    @Override
    public String perm() {
        return annotation.perm();
    }

    @Override
    public int min() {
        return annotation.min();
    }

    @Override
    public int max() {
        return annotation.max();
    }

    @Override
    public boolean equals(Object obj) {
        return annotation.equals(obj);
    }

    @Override
    public int hashCode() {
        return annotation.hashCode();
    }

    @Override
    public String toString() {
        return annotation.toString();
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return annotation.annotationType();
    }
}
