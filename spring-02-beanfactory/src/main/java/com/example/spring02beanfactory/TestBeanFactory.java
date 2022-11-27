package com.example.spring02beanfactory;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @time: 2022/11/25
 * @author: yuanyongan
 * @description: 学习BeanFactory的具体使用及实现方法，这只是一个基础的工具，很多
 * 其他的处理工具都需要手动的添加去处理一些注解
 */
public class TestBeanFactory {
    public static void main(String[] args) {

        // BeanFactory接口的具体实现类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 获取config的bean对象
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 加入BeanFactory后处理工具，解析注解，获取另外两个bean对象
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

//        // 此时是没有Bean的后处理工具，所以Autowired不会得到解析
//        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        // Bean的后处理工具，针对Bean的生命周期各个阶段提供拓展，比如处理@Autowired,@Resource...
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);


        for (String name : beanFactory.getBeanDefinitionNames()){
            System.out.println(name);
        }


        // 第一次调用getBean的时候会对注解进行解析并构造bean对象，而且会保持结果，所以在加入后处理工具之前不能进行调用
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
    }


    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1() {return new Bean1();}

        @Bean
        public Bean2 bean2() {return new Bean2();}
    }

    static class Bean1{
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1(){
            log.debug("构造Bean1");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){return bean2;}
    }

    static class Bean2{
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean2(){
            log.debug("构造Bean2");
        }

    }
}
