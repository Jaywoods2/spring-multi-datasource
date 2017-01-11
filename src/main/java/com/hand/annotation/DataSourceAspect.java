package com.hand.annotation;

import com.hand.annotation.DataSource;
import com.hand.util.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by jaywoods on 2017/1/10.
 */

public class DataSourceAspect {

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }


    private void resolveDataSource(Class<?> clazz, Method method) throws Exception {
        Class<?>[] types = method.getParameterTypes();
        //类注解
        if (clazz.isAnnotationPresent(DataSource.class)) {
            //根据DataSource注解值切换数据库
            DataSource source = clazz.getAnnotation(DataSource.class);
            DataSourceContextHolder.setDataSourceType(source.value());
        }
        //方法注解
        Method m = clazz.getMethod(method.getName(), types);
        if (m != null && m.isAnnotationPresent(DataSource.class)) {
            DataSource source = m.getAnnotation(DataSource.class);
            DataSourceContextHolder.setDataSourceType(source.value());
        }
    }
}
