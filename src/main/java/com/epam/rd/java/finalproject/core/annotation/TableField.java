package com.epam.rd.java.finalproject.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {
    String dbFieldName();

    int insertPosition() default 0;

    int updatePosition() default 0;

    int select() default 0;
}
