package com.example.spring04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description:
 */

@Slf4j
public class Bean1 {
    private Bean2 bean2;
    private Bean3 bean3;

    @Autowired
    public void setBean2(Bean2 bean2){
        log.info("@Autowired 生效： {}",bean2);
        this.bean2 = bean2;
    }

    private String java_home;
    @Autowired
    public void setJava_home(@Value("${JAVA_HOME}") String java_home){
        log.info("@Value生效： {}", java_home);
        this.java_home = java_home;
    }

    @Resource
    public void setBean3(Bean3 bean3){
        log.info("@Autowired 生效： {}",bean3);
        this.bean3 = bean3;
    }

    @PostConstruct
    public void init(){
        log.info("@PostConstruct生效");
    }

    @PreDestroy
    public void destroy(){
        log.info("@PreDestroy生效");
    }

    @Override
    public String toString(){
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", java_home='" + java_home + '\'' +
                '}';
    }

}
