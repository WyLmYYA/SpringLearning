package com.example.spring05;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @time: 2022/11/27
 * @author: yuanyongan
 * @description: 对BeanFactory的后处理器进行学习，
 * 主要分为@ComponentSan和@Mapper 以及@Bean注解
 * 通过模拟对底层逻辑进行梳理
 */
public class TestBeanFactoryPostProcessor {

    public static void main(String[] args) {
//        GenericApplicationContext context = new GenericApplicationContext();
//        context.registerBean("config", Config.class);
//
//        // 添加BeanFactory后处理器ConfigurationClassPostProcessor
//        // 解析@CommponentScan @Bean @Import @ImportResource
//        context.registerBean(ConfigurationClassPostProcessor.class);
//        // 添加BeanFactory后处理器MapperScannerConfigurer，解析@MapperScan注解
//        context.registerBean(MapperScannerConfigurer.class, beanDefinition ->{
//            // 指定扫描的包名
//            beanDefinition.getPropertyValues().add("basePackage", "com.example.spring05.mapper");
//        });
//
//        context.refresh();
//
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//
//        context.close();
        TestMockBeanFactoryPostProcessor();
    }

    // 模拟组件扫描，即@ComponentSacan, @Bean, @Mapper注解的解析
    public static void TestMockBeanFactoryPostProcessor(){
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);

        // 把自定义的组件扫描BeanFactory后处理器注册进去
        context.registerBean(CandyAtComponentScanPostProcessor.class);
        context.registerBean(CandyAtBeanPostProcessor.class);
        context.registerBean(CandyAtMapperPostProcessor.class);

        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        context.close();

    }

}
