package com.example.spring04;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description: AutowiredAnnotationBeanPostProcessor是对Autowired注解进行后处理的方法
 * 对其内部的函数进行使用来进行简单的学习
 */
public class TestAutowiredAnnotationBeanPostProcessor {
    public static void main(String[] args) throws Throwable {
        // 使用beanFactory方便学习，不然其他的ApplicationContext都封装好了
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());

        // 设置@Autowired的解析器，解析器和后处理器不一样，解析器是在后处理器中用到的
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 设置@Value中的${}解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // 处理器实例
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();

        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.out.println(bean1);

        // 通过反射对private方法进行使用
        // findAutowiringMetadata先找到带有@Autowired的对象
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);

        // 获取添加了@Value @Autowired注解的成员变量和方法参数信息
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);

        System.out.println(metadata);

        // 将扫描到的添加了@Value @Autowired的对象进行注入
        metadata.inject(bean1, "bean1",null);

        System.out.println(bean1);
    }

}
