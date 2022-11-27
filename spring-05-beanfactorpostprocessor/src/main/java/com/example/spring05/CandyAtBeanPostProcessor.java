package com.example.spring05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @time: 2022/11/27
 * @author: yuanyongan
 * @description: 解析@Bean注解的自定义BeanFactory后处理器
 */
public class CandyAtBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        try {
            // 获取类注解的工厂类
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
            // 可以用Component中的弹性路径，这里写死了
            MetadataReader reader = factory.getMetadataReader(new ClassPathResource("com/example/spring05/Config.class"));
            // 得到所有添加了@Bean注解的信息
            Set<MethodMetadata> annotateMethods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
            for (MethodMetadata annotateMethod : annotateMethods) {
                System.out.println(annotateMethod);
                // 获取@Bean注解中的所有属性取值
                String initMethod = annotateMethod.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();

                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
                // 调用工厂方法的名字
                builder.setFactoryMethodOnBean(annotateMethod.getMethodName(), "config");

                // 设置有参数的工厂方法也可以被传入
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

                if (initMethod.length() > 0){
                    builder.setInitMethodName(initMethod);
                }

                // 注册Bean进入容器
                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                beanFactory.registerBeanDefinition(annotateMethod.getMethodName(), beanDefinition);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
