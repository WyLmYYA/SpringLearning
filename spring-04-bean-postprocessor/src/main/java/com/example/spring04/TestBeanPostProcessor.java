package com.example.spring04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description: 对Bean的后处理器进行添加学习
 * 在一般的ApplicationContext中后处理器都是已经加好的
 * 这里使用GenericApplicationContext是一个比较空白的ApplicationContext来进行学习
 */
public class TestBeanPostProcessor {
    public static void main(String[] args) {
        testBeanPostProcessor();
    }
    public static void testBeanPostProcessor() {

        // 空白容器，不会添加任何后处理器
        GenericApplicationContext context = new GenericApplicationContext();
        // 用初始方法注册四个Bean,在这里，注解下面的方法log日志都不会被打印
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        //设置解析@Value 注解的解析器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // 添加解析@Autowired 和 @Value注解的后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        //添加解析 @Resource @PostConstruct @PreDestroy 注解的后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // 添加解析@ConfigurationProperties注解的后处理器
        // ConfigurationPropertiesBindingPostProcessor后处理器不能像上面几种后处理器那样用context直接注册上去
        // 需要反着来注册一下
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // 初始化容器，对后处理器进行添加
        context.refresh();

        System.out.println(context.getBean(Bean4.class));
        // 销毁容器
        context.close();
    }


}
