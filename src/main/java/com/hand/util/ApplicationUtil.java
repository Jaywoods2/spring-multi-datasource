package com.hand.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jaywoods on 2017/1/10.
 */
public class ApplicationUtil{

    private static ApplicationContext context;

    public static ApplicationContext startContext() {
        context= new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        return context;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static Object getBean(Class<?> aClass){
        return context.getBean(aClass);
    }
}
