package com.example.spring02applicationcontext;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 * @time: 2022/11/25
 * @author: yuanyongan
 * @description: 对各种ApplicationContext进行学习
 * ApplicationContext其实是对BeanFactory更好的封装，让我们可以通过各种方式直接对Bean进行注入
 *
 */
public class TestApplicationContext {
    public static void main(String[] args) {

//        testClassPathXmlApplicationContext();

//        testFileSystemXmlApplicationContext();

//        testAnnotationConfigApplicationContext();

        testAnnotationConfigServletWebApplicationContext();
        /**
         * ApplicationContext其实是封装了BeanFactory的使用方法，具体如下
         */
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        System.out.println("读取文件之前...");
//        for (String name : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//        System.out.println("读取xml文件之后...beans注入容器中");
//        // 选择注入factory中
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
//        for (String name : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }

    /**
     * 通过读取Xml文件对bean进行注入，这里对应的文件是在resources目录下的xml文件
     */
    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(context.getBean(Bean2.class).getBean1());

    }

    /**
     * 通过Xml文件进行bean注入，但是这里的路径是文件的路径
     */
    private static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans.xml");

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    /**
     * 通过注解注入Bean对象
     */
    private static void testAnnotationConfigApplicationContext(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(context.getBean(Bean2.class).getBean1());

    }

    /**
     * 内嵌web容器的注解注入bean对象，springboot中通常使用
     */
    private static void testAnnotationConfigServletWebApplicationContext(){
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig{

        // Tomcat容器
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }

        // 注册路径
        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller controller1(){

            return (request, response) -> {
                response.getWriter().print("hello");
                return null;
            };
        }
    }


    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }
    static class Bean1{}

    static class Bean2{
        private Bean1 bean1;

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
        public Bean1 getBean1(){
            return bean1;
        }
    }
}
