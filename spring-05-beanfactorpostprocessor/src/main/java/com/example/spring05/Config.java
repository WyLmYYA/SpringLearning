package com.example.spring05;


import com.alibaba.druid.pool.DruidDataSource;
import com.example.spring05.component.Bean2;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @time: 2022/11/27
 * @author: yuanyongan
 * @description:
 */

@Configuration
@ComponentScan("com.example.spring05.component")
public class Config {
    @Bean
    public Bean1 bean1(){
        return new Bean1();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
    @Bean(initMethod = "init")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost::3306/springtest");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    public Bean2 bean2(){
        return new Bean2();
    }


}
