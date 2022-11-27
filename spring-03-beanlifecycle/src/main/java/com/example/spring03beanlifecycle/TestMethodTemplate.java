package com.example.spring03beanlifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description: 对Bean的生命周期进行模板化开发，提高代码的拓展性
 * 这里是通过注解解析的拓展对模板方法的简单学习
 */
public class TestMethodTemplate {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("对@Autowired注解进行解析");
            }
        });
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("对@Resource注解进行解析");
            }
        });
        beanFactory.getBean();
    }
    static class MyBeanFactory{
        public Object getBean(){
            Object bean = new Object();
            System.out.println("构造 " + bean);
            System.out.println("依赖注入 " + bean);
            for (BeanPostProcessor processor : processors) {
                processor.inject(bean);
            }
            System.out.println("初始化 " + bean);
            return bean;
        }
        private List<BeanPostProcessor> processors = new ArrayList<>();

        public void addBeanPostProcessor(BeanPostProcessor processor){
            processors.add(processor);
        }
    }
    static interface BeanPostProcessor{
        public void inject(Object bean);
    }
}
