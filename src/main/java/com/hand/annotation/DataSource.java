package com.hand.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by jaywoods on 2017/1/10.
 */

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface DataSource {
    String value();
}
