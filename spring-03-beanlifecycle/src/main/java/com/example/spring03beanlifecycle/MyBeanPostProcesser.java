package com.example.spring03beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description:
 */

@Slf4j
@Component
public class MyBeanPostProcesser implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<销毁之前执行");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<实例化前执行，比如@PreDestroy");
        // 返回null保持原有对象不变，返回不为null，则会替换掉原来的对象
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<实例化后执行，如果返回false会跳过依赖注入阶段");
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<依赖注入执行阶段，如@Autowired,@Value,@Resource");
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<初始化之前执行，这里返回的对象会替换掉原本的bean，如@PostConstruct，@ConfigurationProperties");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.info("<<<<<<<<<初始化之后执行，这里返回的对象会替换掉原本的bean，如代理增强");
        return bean;
    }
}
