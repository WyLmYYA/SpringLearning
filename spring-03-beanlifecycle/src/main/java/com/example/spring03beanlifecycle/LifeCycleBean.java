package com.example.spring03beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description: Bean生命周期的学习
 */

@Slf4j
@Component
public class LifeCycleBean {
    public LifeCycleBean(){log.info("构造方法");}

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home){
        log.info("依赖注入：{}",home);
    }

    @PostConstruct
    public void init(){ log.info("初始化");}

    @PreDestroy
    public void destroy(){ log.info("销毁"); }

}
